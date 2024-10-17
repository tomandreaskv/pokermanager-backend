package no.vingaas.pokermanager.service.auth

import no.vingaas.pokermanager.common.request.AuthenticationRequest
import no.vingaas.pokermanager.common.response.AuthenticationResponse
import no.vingaas.pokermanager.config.JwtProperties
import no.vingaas.pokermanager.entities.auth.AccessToken
import no.vingaas.pokermanager.entities.auth.RefreshToken
import no.vingaas.pokermanager.entities.auth.TokenStatus
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.repository.auth.AccessTokenRepository
import no.vingaas.pokermanager.repository.auth.RefreshTokenRepository
import no.vingaas.pokermanager.service.security.CustomUserDetailsService
import no.vingaas.pokermanager.service.security.TokenService
import no.vingaas.pokermanager.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

@Service
class AuthenticationServiceImpl(
    private val authenticationManager : AuthenticationManager,
    private val userDetailsService : CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val accessTokenRepository: AccessTokenRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userService: UserService
): AuthenticationService{
    private val logger = LoggerFactory.getLogger(AuthenticationServiceImpl::class.java)
    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        logger.info("Authenticating user: ${request.email}")
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val userDetails = userDetailsService.loadUserByUsername(request.email)
        val user : User = userService.getUserByEmail(request.email)

        val accessToken = generateAccessToken(userDetails)
        val refreshToken = generateRefreshToken(userDetails)

        logger.info("User authenticated: ${request.email}")
        logger.debug("Generated token: $accessToken")

        // Lagre refresh token i databasen
        val refreshTokenEn = saveRefreshToken(refreshToken, user)

        // Lagre access token i databasen
        saveAccessToken(accessToken, user, refreshTokenEn)


        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun generateAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expiration =  LocalDateTime.now().plusSeconds(jwtProperties.accessTokenExpiration / 1000)
    )

    private fun generateRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expiration = LocalDateTime.now().plusSeconds(jwtProperties.refreshTokenExpiration / 1000)
    )

    // Lagre access token i databasen
    private fun saveAccessToken(accessToken: String, user: User, refreshToken: RefreshToken) {
        val expiration = convertDateToLocalDateTime(tokenService.getExpirationFromToken(accessToken))
        val jti = tokenService.getJtiFromToken(accessToken)
        if (jti != null) {
            val accessTokenEntity = AccessToken(
                jti = jti,
                user = user,
                refreshToken = refreshToken,
                expiration = expiration,
                createdAt = LocalDateTime.now(),
                status = TokenStatus.VALID
            )
            accessTokenRepository.save(accessTokenEntity)
        }
    }

    // Lagre refresh token i databasen
    private fun saveRefreshToken(refreshToken: String, user: User):RefreshToken {
        val expiration = convertDateToLocalDateTime(tokenService.getExpirationFromToken(refreshToken))
        val refreshTokenEntity = RefreshToken(
            token = refreshToken,
            user = user,
            expiration = expiration,
            createdAt = LocalDateTime.now(),
            status = TokenStatus.VALID
        )
       return refreshTokenRepository.save(refreshTokenEntity)
    }

    override fun refreshToken(token: String): AuthenticationResponse? {
        val extractedEmail = tokenService.extractEmail(token)
        return extractedEmail?.let { email ->
            val currentUser = userDetailsService.loadUserByUsername(email)
            val refreshTokenEntity = refreshTokenRepository.findByToken(token)
            val refreshTokenUser: User = refreshTokenEntity?.user ?: return null

            if (!tokenService.isTokenExpired(token) && currentUser.username == refreshTokenUser.email) {
                // Ugyldiggjør gamle access tokens
                val oldAccessTokens = accessTokenRepository.findAllByUserIdAndStatus(refreshTokenUser.id, TokenStatus.VALID)

                oldAccessTokens.forEach { accessToken ->
                    accessToken.status = TokenStatus.INVALID
                }
                accessTokenRepository.saveAll(oldAccessTokens)

                // Generer nytt access token
                val newAccessToken = generateAccessToken(currentUser)

                // Generer nytt refresh token hvis det eksisterende er i ferd med å utløpe
                val newRefreshToken = if (isRefreshTokenExpiringSoon(refreshTokenEntity)) {
                    // Ugyldiggjør det gamle refresh tokenet
                    refreshTokenEntity.status = TokenStatus.INVALID
                    refreshTokenRepository.save(refreshTokenEntity)

                    val newToken = generateRefreshToken(currentUser)
                    val newRefreshTokenEntity = saveRefreshToken(newToken, refreshTokenUser)
                    newRefreshTokenEntity.token // Returner det nye refresh tokenet
                } else {
                    refreshTokenEntity.token // Bruk eksisterende refresh token
                }

                // Lagring av det nye access tokenet i databasen
                saveAccessToken(newAccessToken, refreshTokenUser, refreshTokenEntity)

                // Returner både access og refresh token
                return AuthenticationResponse(
                    accessToken = newAccessToken,
                    refreshToken = newRefreshToken
                )
            } else {
                logger.warn("Invalid or expired refresh token for user: $email")
                return null
            }
        }
    }

    private fun isRefreshTokenExpiringSoon(refreshTokenEntity: RefreshToken): Boolean {
        val timeLeft = refreshTokenEntity.expiration.toEpochSecond(ZoneOffset.UTC) - LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val totalTime = refreshTokenEntity.expiration.toEpochSecond(ZoneOffset.UTC) - refreshTokenEntity.createdAt.toEpochSecond(ZoneOffset.UTC)

        // Forny token hvis mindre enn 20% av levetiden er igjen
        return timeLeft.toDouble() / totalTime.toDouble() < 0.2
    }

    override fun invalidateToken(jti: String) {
        val tokenEntity = accessTokenRepository.findByJti(jti)
        if (tokenEntity != null) {
            tokenEntity.status = TokenStatus.INVALID  // Sett status til invalid
            accessTokenRepository.save(tokenEntity)  // Oppdater token i databasen
        }
    }

    override fun logout(token: String) {
        val jti = tokenService.getJtiFromToken(token)

        // Sjekk om jti er null, hvis ja, avslutt tidlig
        if (jti == null) {
            logger.warn("Token mangler JTI, kan ikke logge ut.")
            return
        }

        // Hent token fra databasen ved hjelp av jti
        val tokenEntity = accessTokenRepository.findByJti(jti)

        // Hvis tokenEntity er null, returner og logg advarsel
        if (tokenEntity == null) {
            logger.warn("Fant ikke access token med JTI: $jti")
            return
        }

        // Sett token status til ugyldig
        tokenEntity.status = TokenStatus.INVALID

        // Lagre oppdatert token status i databasen
        accessTokenRepository.save(tokenEntity)

        logger.info("Access token med JTI $jti er ugyldiggjort.")
    }


    fun convertDateToLocalDateTime(date: Date): LocalDateTime {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    fun logoutAllDevices(user: User) {
        val accessTokens = accessTokenRepository.findAllByUserIdAndStatus(user.id, TokenStatus.VALID)
        val refreshTokens = refreshTokenRepository.findAllByUserIdAndStatus(user.id, TokenStatus.VALID)

        accessTokens.forEach { it.status = TokenStatus.INVALID }
        refreshTokens.forEach { it.status = TokenStatus.INVALID }

        accessTokenRepository.saveAll(accessTokens)
        refreshTokenRepository.saveAll(refreshTokens)
    }
}
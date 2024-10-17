package no.vingaas.pokermanager.service.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import no.vingaas.pokermanager.config.JwtProperties
import no.vingaas.pokermanager.entities.auth.TokenStatus
import no.vingaas.pokermanager.repository.auth.AccessTokenRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class TokenServiceImpl(
    jwtProperties: JwtProperties,
    private val accessTokenRepository: AccessTokenRepository,
    private val userDetailsService: CustomUserDetailsService
): TokenService{
    private val logger = LoggerFactory.getLogger(TokenServiceImpl::class.java)
    private val secret = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    override fun generate(userDetails: UserDetails, expiration: LocalDateTime, additionalClaims: Map<String, Any>): String{
        logger.debug("Generating token for user: ${userDetails.username}")

        val jti = UUID.randomUUID().toString()  // Genererer en unik jti

        return  Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant()))
            .id(jti)
            .add(additionalClaims)
            .and()
            .signWith(secret)
            .compact()
    }

    private fun getAllClaimsFromToken(token: String) : Claims {
        logger.debug("Getting all claims from token")
       val parser = Jwts.parser()
            .verifyWith(secret)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }

    override fun extractEmail(token: String): String? {
        logger.debug("Extracting email from token")
        return getAllClaimsFromToken(token)
            .subject
    }


    override fun isTokenExpired(token: String): Boolean{
        logger.debug("Checking if token is expired")
        return getAllClaimsFromToken(token)
            .expiration
            .before(Date(System.currentTimeMillis()))
    }

    // Tilpasset metode for å hente ut hvilken som helst claim basert på nøkkel
    override fun getCustomClaimFromToken(token: String, claimKey: String): Any? {
        logger.debug("Extracting $claimKey from token")
        return getAllClaimsFromToken(token)[claimKey]  // Hent claim basert på nøkkel
    }

    override fun getExpirationFromToken(token: String): Date {
        logger.debug("Extracting expiration from token")
        return getAllClaimsFromToken(token).expiration  // Hent expiration claim fra tokenet
    }

    override fun getJtiFromToken(token: String): String? {
        logger.debug("Extracting jti from token")
        return getAllClaimsFromToken(token).id  // Hent jti fra tokenet ved å bruke .id
    }


    override fun isValid(token: String, userDetails: UserDetails): Boolean {
        logger.debug("Checking if token is valid")
        val email = extractEmail(token)
        val jti = getJtiFromToken(token)

        if (email != userDetails.username || isTokenExpired(token)|| jti == null) {
            return false
        }

        // Sjekk om tokenet finnes i databasen og er gyldig
        val tokenEntity = accessTokenRepository.findByJti(jti ?: "")
        return tokenEntity != null && tokenEntity.status == TokenStatus.VALID
    }

}


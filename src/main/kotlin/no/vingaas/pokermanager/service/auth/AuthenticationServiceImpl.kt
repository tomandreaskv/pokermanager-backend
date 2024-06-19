package no.vingaas.pokermanager.service.auth

import no.vingaas.pokermanager.common.request.AuthenticationRequest
import no.vingaas.pokermanager.common.response.AuthenticationResponse
import no.vingaas.pokermanager.config.JwtProperties
import no.vingaas.pokermanager.service.security.CustomUserDetailsService
import no.vingaas.pokermanager.service.security.TokenService
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthenticationServiceImpl(
    private val authenticationManager : AuthenticationManager,
    private val userDetailsService : CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
): AuthenticationService{
    private val logger = LoggerFactory.getLogger(AuthenticationServiceImpl::class.java)
    override fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        logger.info("Authenticating user: ${request.email}")
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.password
            )
        )

        val user = userDetailsService.loadUserByUsername(request.email)
        val token = tokenService.generate(
            userDetails = user,
            expiration = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )
        logger.info("User authenticated: ${request.email}")
        logger.debug("Generated token: $token")
        return AuthenticationResponse(
            accessToken = token
        )
    }
}
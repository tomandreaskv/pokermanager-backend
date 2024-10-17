package no.vingaas.pokermanager.controller.auth

import no.vingaas.pokermanager.common.request.AuthenticationRequest
import no.vingaas.pokermanager.common.request.RefreshTokenRequest
import no.vingaas.pokermanager.common.response.AuthenticationResponse
import no.vingaas.pokermanager.common.response.TokenResponse
import no.vingaas.pokermanager.service.auth.AuthenticationService
import no.vingaas.pokermanager.service.security.TokenService
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationservice : AuthenticationService,
    private val tokenService: TokenService
) {
    @PostMapping
    fun authenticate(@RequestBody request: AuthenticationRequest): AuthenticationResponse {
        return authenticationservice.authenticate(request)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): AuthenticationResponse {
        return authenticationservice.refreshToken(request.token)
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token")
    }


    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authHeader: String): HttpStatus {
        // Ekstraher JWT-token fra Authorization-headeren
        val token = authHeader.replace("Bearer ", "")

        authenticationservice.logout(token)

        // Fjern autentisering fra sikkerhetskonteksten
        SecurityContextHolder.clearContext()

        return HttpStatus.OK  // Returner suksess
    }
    private fun String.mapToTokenResponse() = TokenResponse(this)
}
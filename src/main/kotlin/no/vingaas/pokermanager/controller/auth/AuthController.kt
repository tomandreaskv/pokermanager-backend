package no.vingaas.pokermanager.controller.auth

import no.vingaas.pokermanager.common.request.AuthenticationRequest
import no.vingaas.pokermanager.common.response.AuthenticationResponse
import no.vingaas.pokermanager.service.auth.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationservice : AuthenticationService
) {
    @PostMapping
    fun authenticate(@RequestBody request: AuthenticationRequest): AuthenticationResponse {
        return authenticationservice.authenticate(request)
    }

}
package no.vingaas.pokermanager.service.auth

import no.vingaas.pokermanager.common.request.AuthenticationRequest
import no.vingaas.pokermanager.common.response.AuthenticationResponse
import org.springframework.stereotype.Service

@Service
interface AuthenticationService {

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse
    fun refreshToken(token: String): AuthenticationResponse?
    fun invalidateToken(jti: String)

    fun logout(token: String)

}

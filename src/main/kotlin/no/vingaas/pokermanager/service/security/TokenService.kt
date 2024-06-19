package no.vingaas.pokermanager.service.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
interface TokenService {
    fun generate(
        userDetails: UserDetails,
        expiration: Date,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String

    fun extractEmail(token: String): String?

    fun isTokenExpired(token: String): Boolean

    fun isValid(token: String, userDetails: UserDetails): Boolean
}
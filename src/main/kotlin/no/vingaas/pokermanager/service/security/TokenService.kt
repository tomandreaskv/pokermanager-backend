package no.vingaas.pokermanager.service.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
interface TokenService {
    fun generate(
        userDetails: UserDetails,
        expiration: LocalDateTime,
        additionalClaims: Map<String, Any> = emptyMap()
    ): String

    fun extractEmail(token: String): String?

    fun getExpirationFromToken(token: String): Date

    fun getCustomClaimFromToken(token: String, claimKey: String): Any?

    fun getJtiFromToken(token: String): String?

    fun isTokenExpired(token: String): Boolean

    fun isValid(token: String, userDetails: UserDetails): Boolean
}
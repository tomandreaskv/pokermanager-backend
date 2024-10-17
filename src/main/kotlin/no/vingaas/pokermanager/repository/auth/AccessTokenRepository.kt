package no.vingaas.pokermanager.repository.auth

import no.vingaas.pokermanager.entities.auth.AccessToken
import no.vingaas.pokermanager.entities.auth.TokenStatus
import org.springframework.data.jpa.repository.JpaRepository

interface AccessTokenRepository : JpaRepository<AccessToken, Long> {
    fun findByJti(jti: String): AccessToken?  // Henter et token basert på jti
    fun findAllByUserIdAndStatus(userId: Long, status: TokenStatus): List<AccessToken>
}
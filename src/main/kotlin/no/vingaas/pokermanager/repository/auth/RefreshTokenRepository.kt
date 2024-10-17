package no.vingaas.pokermanager.repository.auth

import no.vingaas.pokermanager.entities.auth.RefreshToken
import no.vingaas.pokermanager.entities.auth.TokenStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, Long> {

   fun findByToken(token: String): RefreshToken?

   fun findAllByUserIdAndStatus(userId: Long, status: TokenStatus): List<RefreshToken>


}
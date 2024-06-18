package no.vingaas.pokermanager.repository.user

import no.vingaas.pokermanager.entities.user.UserCredential
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserCredentialRepository : JpaRepository<UserCredential, Long> {

    @Query("select u from UserCredential u where u.userId = ?1 and u.isActive = true")
    fun findByUserId(userId: Long): Optional<UserCredential>

    fun findByUserUsername(username: String): UserCredential?
}
package no.vingaas.pokermanager.repository.user

import no.vingaas.pokermanager.entities.user.UserCredential
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCredentialRepository : JpaRepository<UserCredential, Long> {
    fun findByUserUsername(username: String): UserCredential?
}
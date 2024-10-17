package no.vingaas.pokermanager.repository.user

import no.vingaas.pokermanager.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.adminPermissions")
    fun findAllUsers(): List<User>
}
package no.vingaas.pokermanager.repository.user

import no.vingaas.pokermanager.entities.user.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRoleRepository : JpaRepository<UserRole, Long>
{
    fun findByName(name: String): UserRole?
}
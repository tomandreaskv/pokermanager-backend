package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserRole
import org.springframework.stereotype.Service

@Service
interface UserRoleService {
    fun findByName(name: String): UserRole?

    fun save(userRole: UserRole): UserRole

    fun findAll(): List<UserRole>

    fun findById(id: Long): UserRole?

    fun deleteById(id: Long)

    fun update(userRole: UserRole): UserRole
}
package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.entities.user.UserRole

interface UserService {
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun save(user: User): User
    fun delete(user: User)
    fun findAll(): List<User>
    fun findById(id: Long): User?
    fun update(user: User): User
    fun getUserByEmail(email: String): User
    fun updateRole(userId: Long, newRoleName: String): User

    fun updateUserRole(user: User, newRole: UserRole)
}
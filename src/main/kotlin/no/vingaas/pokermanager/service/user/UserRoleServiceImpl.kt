package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserRole
import no.vingaas.pokermanager.repository.user.UserRoleRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserRoleServiceImpl(
    private val userRoleRepository: UserRoleRepository
) : UserRoleService {
    private val logger = LoggerFactory.getLogger(UserRoleServiceImpl::class.java)
    override fun findByName(name: String): UserRole? {
        logger.info("Finding user role by name: $name")
        return userRoleRepository.findByName(name)
    }

    override fun save(userRole: UserRole): UserRole {
        logger.info("Saving user role")
        return userRoleRepository.save(userRole)
    }

    override fun findAll(): List<UserRole> {
        logger.info("Finding all user roles")
        return userRoleRepository.findAll()
    }

    override fun findById(id: Long): UserRole? {
        logger.info("Finding user role by id: $id")
        return userRoleRepository.findById(id).orElse(null)
    }

    override fun deleteById(id: Long) {
        logger.info("Deleting user role by id: $id")
        userRoleRepository.deleteById(id)
    }

    override fun update(userRole: UserRole): UserRole {
        logger.info("Updating user role")
        return userRoleRepository.save(userRole)
    }
}
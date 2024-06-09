package no.vingaas.pokermanager.service.user

import jakarta.transaction.Transactional
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.exception.user.InvalidUserException
import no.vingaas.pokermanager.repository.user.UserRepository
import no.vingaas.pokermanager.validator.user.UserValidator
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository, private val validator: UserValidator) : UserService  {
    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    override fun findByUsername(username: String): User? {
        logger.info("Finding user by username: $username")
        return userRepository.findByUsername(username)
    }

    override fun findByEmail(email: String): User? {
        logger.info("Finding user by email: $email")
        return userRepository.findByEmail(email)
    }

    override fun existsByUsername(username: String): Boolean {
        logger.info("Checking existence by username: $username")
        return userRepository.existsByUsername(username)
    }

    override fun existsByEmail(email: String): Boolean {
        logger.info("Checking existence by email: $email")
        return userRepository.existsByEmail(email)
    }

    @Transactional
    override fun save(user: User): User {
        logger.info("Saving user: ${user.username}")
        val errors = validator.validate(user)
        if (errors.isNotEmpty()) {
            logger.error("Invalid user data: ${errors.joinToString(", ")}")
            throw InvalidUserException("Invalid user data: ${errors.joinToString(", ")}")
        }
        return userRepository.save(user)
    }

    @Transactional
    override fun update(user: User): User {
        logger.info("Updating user: ${user.username}")
        val errors = validator.validate(user)
        if (errors.isNotEmpty()) {
            logger.error("Invalid user data: ${errors.joinToString(", ")}")
            throw InvalidUserException("Invalid user data: ${errors.joinToString(", ")}")
        }
        return userRepository.save(user)
    }

    @Transactional
    override fun delete(user: User) {
        logger.info("Deleting user: ${user.username}")
        userRepository.delete(user)
    }

    override fun findAll(): List<User> {
        logger.info("Finding all users")
        return userRepository.findAll()
    }

    override fun findById(id: Long): User? {
        logger.info("Finding user by id: $id")
        return userRepository.findById(id).orElse(null)
    }
}
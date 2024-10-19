package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserCredential
import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserCredentialServiceImpl(
    private val userCredentialRepository: UserCredentialRepository,
    private val userService: UserService
) : UserCredentialService {
    private val logger = LoggerFactory.getLogger(UserCredentialServiceImpl::class.java)
    private val passwordEncoder = BCryptPasswordEncoder()

    override fun getCredentialsById(id: Long): UserCredential {
        logger.info("Finding user credential by id: $id")
        return userCredentialRepository.findById(id).orElseThrow {
            logger.error("User credential not found")
            throw IllegalArgumentException("User credential not found")
        }
    }

    override fun getCredentialsByUserId(userId: Long): UserCredential {
        logger.info("Finding user credential by user id: $userId")
        return userCredentialRepository.findByUserId(userId)
            .orElseThrow { IllegalArgumentException("User credential not found for user with id: $userId") }
    }

    override fun save(userCredential: UserCredential): UserCredential {
        logger.info("Saving user credential")
        val encryptedCredential = userCredential.copy(password = passwordEncoder.encode(userCredential.password))
        return userCredentialRepository.save(encryptedCredential)
    }

    override fun update(userCredential: UserCredential): UserCredential {
        logger.info("Updating user credential")
        return userCredentialRepository.save(userCredential)
    }

    override fun delete(userCredentialId: Long): Boolean {
        logger.info("Deleting user credential")
        userCredentialRepository.deleteById(userCredentialId)
        return true
    }

    override fun createTemporaryPassword(userId: Long): String {
        val userCredential = userCredentialRepository.findByUserId(userId)
            .orElseThrow { IllegalArgumentException("User credential not found for user with id: $userId") }

        // Deaktiver det gamle passordet
        val deactivatedOldCredential = userCredential.copy(
            isActive = false  // Sett det gamle passordet som inaktivt
        )
        userCredentialRepository.save(deactivatedOldCredential)

        val temporaryPassword = generateTemporaryPassword()
        val encryptedTemporaryPassword = passwordEncoder.encode(temporaryPassword)

        val updatedCredential = userCredential.copy(
            password = encryptedTemporaryPassword,
            isTemporal = true,
            isActive = true,
            validToDateTime = LocalDateTime.now().plusMinutes(30)  // Passordet er gyldig i 30 minutter
        )
        userCredentialRepository.save(updatedCredential)

        // TODO: Send det midlertidige passordet til brukeren på e-post

        return temporaryPassword
    }

    override fun validateTemporaryPassword(userId: Long, password: String): Boolean {
        val userCredential = userCredentialRepository.findByUserId(userId)
            .orElseThrow { IllegalArgumentException("User credential not found for user with id: $userId") }

        return if (userCredential.isTemporal && userCredential.validToDateTime != null) {
            if (userCredential.validToDateTime.isBefore(LocalDateTime.now())) {
                logger.warn("Temporary password has expired for user with id: $userId")
                false
            } else {
                passwordEncoder.matches(password, userCredential.password)
            }
        } else {
            false
        }
    }

    override fun updatePassword(userId: Long, newPassword: String) {
        val userCredential = userCredentialRepository.findByUserId(userId)
            .orElseThrow { IllegalArgumentException("User credential not found for user with id: $userId") }

        val updatedCredential = userCredential.copy(
            password = passwordEncoder.encode(newPassword),
            isTemporal = false,  // Oppdater flagget når passordet er endret
            validToDateTime = null
        )

        userCredentialRepository.save(updatedCredential)
    }

    // Hjelpemetode for å generere et midlertidig passord
    private fun generateTemporaryPassword(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()-_=+"
        return (1..10)
            .map { chars.random() }
            .joinToString("")
    }
}
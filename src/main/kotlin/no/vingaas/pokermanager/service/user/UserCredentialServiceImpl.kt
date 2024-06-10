package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserCredential
import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserCredentialServiceImpl(private val userCredentialRepository: UserCredentialRepository) : UserCredentialService{
    private val logger = org.slf4j.LoggerFactory.getLogger(UserCredentialServiceImpl::class.java)
    private val passwordEncoder = BCryptPasswordEncoder()
    override fun getCredentialsById(id: Long): UserCredential {
        logger.info("Finding user credential by id: $id")
        return userCredentialRepository.findById(id).orElseThrow {
            logger.error("User credential not found")
            throw IllegalArgumentException("User credential not found")
        }
    }

    override fun getCredentialsByUsername(username: String): UserCredential {
        logger.info("Finding user credential by username: $username")
        return userCredentialRepository.findByUserUsername(username) ?: throw IllegalArgumentException("User credential not found")
    }

    override fun save(userCredential: UserCredential): UserCredential {
        logger.info("Saving user credential")
        return userCredentialRepository.save(userCredential)
    }

    override fun update(userCredential: UserCredential): UserCredential {
        logger.info("Updating user credential")
        return userCredentialRepository.save(userCredential)
    }

    override fun delete(userCredential: UserCredential) {
        logger.info("Deleting user credential")
        userCredentialRepository.delete(userCredential)
    }

    private fun generateTempPassword(): String {
        // Implement your temporary password generation logic here
        // This is just a simple example and should not be used in production
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..16)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun sendTempPasswordToUserEmail(email: String, tempPassword: String) {
        // Implement your email sending logic here
        // You might want to use a service like JavaMailSender
    }
}
package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.dto.user.UserCredentialDTO
import no.vingaas.pokermanager.entities.user.UserCredential
import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserCredentialServiceImpl(private val userCredentialRepository: UserCredentialRepository, private val userService: UserService) : UserCredentialService{
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
        return userCredentialRepository.findByUserUsername(username)
            ?: throw IllegalArgumentException("User credential not found")
    }

    override fun getCredentialsByUserId(userId: Long): Optional<UserCredential> {
        logger.info("Finding user credential by user id: $userId")
        return userCredentialRepository.findByUserId(userId)
            ?: throw IllegalArgumentException("User credential not found")
    }

    override fun save(userCredential: UserCredential): UserCredential {
        logger.info("Saving user credential")
        return userCredentialRepository.save(userCredential)
    }

    override fun update(userCredential: UserCredential): UserCredential {
        logger.info("Updating user credential")
        return userCredentialRepository.save(userCredential)
    }

    override fun delete(userCredentialId: Long): Boolean {
        logger.info("Deleting user credential")
        return userCredentialRepository.deleteById(userCredentialId).let { true }
    }

    private fun mapToDTO(userCredential: UserCredential): UserCredentialDTO {
        return UserCredentialDTO(
            userId = userCredential.userId,
            password = userCredential.password,
            isTemporal = userCredential.isTemporal,
            isActive = userCredential.isActive,
            validToDateTime = userCredential.validToDateTime
        )
    }
}
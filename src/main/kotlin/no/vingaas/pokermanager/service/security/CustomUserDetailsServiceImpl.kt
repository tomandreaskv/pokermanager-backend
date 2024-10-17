package no.vingaas.pokermanager.service.security

import no.vingaas.pokermanager.config.CustomUserDetails
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.entities.user.UserCredential
import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import no.vingaas.pokermanager.repository.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Primary
class CustomUserDetailsServiceImpl(
    private val userRepository: UserRepository,
    private val userCredentialRepository: UserCredentialRepository
) :  CustomUserDetailsService {
    private val logger = LoggerFactory.getLogger(CustomUserDetailsServiceImpl::class.java)
    override fun loadUserByUsername(username: String): UserDetails {
        logger.info("Loading user by email: $username")
        val user: User = userRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("User not found with email: $username")

        // Finn UserCredential basert på brukerens ID
        val userCredential: UserCredential = userCredentialRepository.findByUserId(user.id)
            .orElseThrow { IllegalArgumentException("UserCredential not found for user with id ${user.id}") }

        return CustomUserDetails(user, userCredential)
    }
}
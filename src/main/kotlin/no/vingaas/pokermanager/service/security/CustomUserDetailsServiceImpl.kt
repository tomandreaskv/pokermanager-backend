package no.vingaas.pokermanager.service.security

import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import no.vingaas.pokermanager.repository.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

typealias  ApplicationUser = no.vingaas.pokermanager.entities.user.User
@Service
@Primary
class CustomUserDetailsServiceImpl(
    private val userRepository: UserRepository,
    private val userCredentialRepository: UserCredentialRepository
) :  CustomUserDetailsService {
    private val logger = LoggerFactory.getLogger(CustomUserDetailsServiceImpl::class.java)
    override fun loadUserByUsername(username: String): UserDetails {
        logger.info("Loading user by email: $username")
        return userRepository.findByEmail(username)
            ?.mapToUserDetails()
            ?: throw IllegalArgumentException("User not found")
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails {
        logger.info("Mapping user to user details")
        val userCredential =    userCredentialRepository.findByUserId(this.id).get()

        return User.builder()
            .username(this.email)
            .password(userCredential.password)
            .roles(this.role.name)
            .build()
    }

}
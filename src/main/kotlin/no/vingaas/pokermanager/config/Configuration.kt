package no.vingaas.pokermanager.config

import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import no.vingaas.pokermanager.repository.user.UserRepository
import no.vingaas.pokermanager.service.security.CustomUserDetailsService
import no.vingaas.pokermanager.service.security.CustomUserDetailsServiceImpl
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    @Bean
    fun userDetailsService(
        userRepository: UserRepository,
        userCredentialRepository: UserCredentialRepository
    ): CustomUserDetailsService =
        CustomUserDetailsServiceImpl(userRepository, userCredentialRepository)

    @Bean
    fun encoder(): PasswordEncoder = BCryptPasswordEncoder()

   // @Bean
    fun authenticationProvider(userRepository: UserRepository, userCredentialRepository: UserCredentialRepository): AuthenticationProvider =
        DaoAuthenticationProvider()
            .also {
                it.setUserDetailsService(userDetailsService(userRepository, userCredentialRepository))
                it.setPasswordEncoder(encoder())
            }

    @Bean
    fun daoAuthenticationProvider(userRepository: UserRepository, userCredentialRepository: UserCredentialRepository): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService(userRepository, userCredentialRepository))
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    fun AuthenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager
}
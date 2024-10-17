package no.vingaas.pokermanager.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(private val daoAuthenticationProvider: DaoAuthenticationProvider) {

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtAuthenticationFilter: JwtAuthenticationFilter): DefaultSecurityFilterChain =
        http
            .csrf{ it.disable() }
            .authorizeHttpRequests{
                it
                    // Allow access to these endpoints without authentication
                    .requestMatchers("/api/auth", "/api/auth/refresh", "/error").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                    // Allow access to user-related endpoints with user role
                    .requestMatchers("/api/users/**").hasAnyRole("USER", "ADMIN")

                    // Allow access to tournament-related endpoints with user role
                    .requestMatchers("/api/tournaments/**").hasAnyRole("USER", "ADMIN")

                    // Allow access to equipment-related endpoints with user role
                    .requestMatchers("/api/equipments/**").hasAnyRole("USER", "ADMIN")

                    // Allow access to blind structure-related endpoints with user role
                    .requestMatchers("/api/blindstructure/**").hasAnyRole("USER", "ADMIN")

                    // Allow access to ranking-related endpoints with user role
                    .requestMatchers("/api/ranking/**").hasAnyRole("USER", "ADMIN")
                    // Allow access to admin-related endpoints with admin role only
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")

                    // All other requests need to be authenticated
                    .anyRequest().authenticated()
            }
            .sessionManagement{
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(daoAuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
}
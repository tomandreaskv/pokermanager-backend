package no.vingaas.pokermanager.config

import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.entities.user.UserCredential
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    private val user: User, // Koble User-entiteten
    private val userCredential: UserCredential  // Koble UserCredential-entiteten
) : UserDetails {

    // Returnerer brukerens ID
    fun getId(): Long {
        return user.id
    }

    override fun getUsername(): String {
        return user.email // Bruker email som brukernavn
    }

    override fun getPassword(): String {
        return userCredential.password // Hent passordet fra UserCredential eller User
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        // Returner brukerens roller eller autoriteter (kan hente fra UserRole)
        return listOf(SimpleGrantedAuthority(user.role.name))
    }

    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true // Håndter hvis kontoen er deaktivert
}
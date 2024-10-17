package no.vingaas.pokermanager.service.security

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
interface CustomUserDetailsService : UserDetailsService {
}
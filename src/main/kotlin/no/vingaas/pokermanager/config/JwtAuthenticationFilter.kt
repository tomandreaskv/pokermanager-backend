package no.vingaas.pokermanager.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import no.vingaas.pokermanager.service.security.TokenService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    @Qualifier("customUserDetailsServiceImpl") private val userDetailsService: UserDetailsService,
    private val tokenService: TokenService
) : OncePerRequestFilter()
{
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")

        if(authHeader.doesNotContainBearerToken()){
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken = authHeader!!.extractJwtToken()
        val email = tokenService.extractEmail(jwtToken)

        if(email != null && SecurityContextHolder.getContext().authentication == null){
            val foundUser = userDetailsService.loadUserByUsername(email)
            if(tokenService.isValid(jwtToken, foundUser)){
                updateSecurityContext(foundUser, request)
            }
            filterChain.doFilter(request, response)
        }
    }

    private fun updateSecurityContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun String?.doesNotContainBearerToken(): Boolean {
        return this == null || !this.startsWith("Bearer ")
    }

    private fun String.extractJwtToken(): String {
        return this.substringAfter("Bearer ")
    }

}
package no.vingaas.pokermanager.service.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import no.vingaas.pokermanager.config.JwtProperties
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenServiceImpl(
    jwtProperties: JwtProperties
): TokenService{
    private val logger = LoggerFactory.getLogger(TokenServiceImpl::class.java)
    private val secret = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    override fun generate(userDetails: UserDetails, expiration: Date, additionalClaims: Map<String, Any>): String{
        logger.debug("Generating token for user: ${userDetails.username}")
        return  Jwts.builder()
            .claims()
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(expiration)
            .add(additionalClaims)
            .and()
            .signWith(secret)
            .compact()
    }


    private fun getAllClaimsFromToken(token: String) : Claims {
        logger.debug("Getting all claims from token")
       val parser = Jwts.parser()
            .verifyWith(secret)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }

    override fun extractEmail(token: String): String? {
        logger.debug("Extracting email from token")
        return getAllClaimsFromToken(token)
            .subject
    }


    override fun isTokenExpired(token: String): Boolean{
        logger.debug("Checking if token is expired")
        return getAllClaimsFromToken(token)
            .expiration
            .before(Date(System.currentTimeMillis()))
    }


    override fun isValid(token: String, userDetails: UserDetails): Boolean {
        logger.debug("Checking if token is valid")
        val email = extractEmail(token)
        return email == userDetails.username && !isTokenExpired(token)
    }

}


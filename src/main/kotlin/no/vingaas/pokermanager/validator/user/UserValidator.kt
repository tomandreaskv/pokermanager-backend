package no.vingaas.pokermanager.validator.user

import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.validator.Validator
import org.springframework.stereotype.Component

@Component
class UserValidator : Validator<User> {

    override fun validate(t: User): Set<String> {
        val errors = mutableSetOf<String>()

        validateUsername(t.username)?.let { errors.add(it) }
        validateEmail(t.email)?.let { errors.add(it) }

        return errors
    }

    fun validateUsername(username: String): String? {
        return if (username.isBlank()) {
            "Username cannot be blank"
        } else {
            null
        }
    }

    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> {
                "Email cannot be blank"
            }
            !email.contains("@") -> {
                "Email must be a valid email address"
            }
            else -> {
                null
            }
        }
    }
}
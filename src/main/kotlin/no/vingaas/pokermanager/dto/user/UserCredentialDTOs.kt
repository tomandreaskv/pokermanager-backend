package no.vingaas.pokermanager.dto.user

import java.time.LocalDateTime

data class UserCredentialDTO(
    val userId: Long,
    val password: String,
    val isTemporal: Boolean = false,
    val isActive: Boolean = true,
    val validToDateTime: LocalDateTime? = null
)

data class UpdatePasswordDTO(
    val oldPassword: String,
    val newPassword: String
)

data class ForgotPasswordDTO(
    val email: String
)
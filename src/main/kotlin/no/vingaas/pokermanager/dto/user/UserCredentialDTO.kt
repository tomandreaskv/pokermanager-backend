package no.vingaas.pokermanager.dto.user

import java.time.LocalDateTime

data class UserCredentialDTO(
    val userId: Long,
    val password: String,
    val isTemporal: Boolean = false,
    val isActive: Boolean = true,
    val validToDateTime: LocalDateTime? = null
)
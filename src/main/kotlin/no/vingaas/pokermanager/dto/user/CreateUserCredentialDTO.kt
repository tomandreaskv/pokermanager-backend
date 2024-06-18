package no.vingaas.pokermanager.dto.user

data class CreateUserCredentialDTO(
    val userId: Long,
    val password: String
)
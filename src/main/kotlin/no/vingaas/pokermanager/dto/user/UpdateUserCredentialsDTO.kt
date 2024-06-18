package no.vingaas.pokermanager.dto.user

data class UpdateUserCredentialsDTO(
    var userId: Long = 0,
    var oldPassword: String,
    var newPassword: String
)
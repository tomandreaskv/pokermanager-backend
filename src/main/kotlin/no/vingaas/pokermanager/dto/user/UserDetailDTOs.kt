package no.vingaas.pokermanager.dto.user

import java.time.LocalDate

data class UserDetailDTO(
    val firstname: String,
    val lastname: String,
    val dateOfBirth: LocalDate,
    val country: String,
    val phoneNumber: String?,
    val city: String?,
    val address: String?,
    val zipCode: String?,
    val bio: String?,
    val profilePicture: String?
)

data class UpdateUserDetailDTO(
    val firstname: String,
    val lastname: String,
    val phoneNumber: String?,
    val city: String?,
    val address: String?,
    val zipCode: String?,
    val bio: String?,
    val profilePicture: String?
)
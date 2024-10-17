package no.vingaas.pokermanager.dto.user

import java.time.LocalDate

// BasicUserDTO for vanlige brukere
data class BasicUserDTO(
    val firstname: String,
    val lastname: String,
    val country: String
)

// FullUserDTO for administratorer
data class FullUserDTO(
    val id: Long,
    val username: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val dateOfBirth: LocalDate,
    val country: String,
    val phoneNumber: String?,
    val address: String?,
    val city: String?,
    val zipCode: String?,
    val bio: String?,
    val profilePicture: String?,
    val isAdmin: Boolean,
    val role: String
)
package no.vingaas.pokermanager.dto

import java.time.LocalDate

data class UserCreateDTO(
    val username: String,
    val email : String,
    val firstname: String,
    val lastname: String,
    val dateOfBirth: LocalDate,
    val country: String)
package no.vingaas.pokermanager.util

import no.vingaas.pokermanager.entities.common.Status
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.tournament.TournamentSpecification
import no.vingaas.pokermanager.entities.tournament.TournamentType
import no.vingaas.pokermanager.entities.tournament.TournamentVisibility
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.entities.user.UserCredential
import no.vingaas.pokermanager.entities.user.UserDetail
import no.vingaas.pokermanager.entities.user.UserRole
import java.time.LocalDate
import java.time.LocalDateTime

object TestDataFactory {
    // Lager en standard UserDetail, men tillater overstyring av spesifikke verdier
    fun createUserDetail(
        id: Long = 1L,
        firstname: String = "Test",
        lastname: String = "User",
        address: String = "123 Test St.",
        bio: String = "Bio for test user",
        city: String = "Test City",
        country: String = "Test Country",
        dateOfBirth: LocalDate = LocalDate.now().minusYears(30),  // Assuming 30 years old
        phoneNumber: String = "123456789",
        profilePicture: String = "test_profile.jpg",
        zipCode: String = "12345"
    ): UserDetail {
        return UserDetail(
            id = id,
            firstname = firstname,
            lastname = lastname,
            address = address,
            bio = bio,
            city = city,
            country = country,
            dateOfBirth = dateOfBirth,
            phoneNumber = phoneNumber,
            profilePicture = profilePicture,
            zipCode = zipCode
        )
    }
    fun createTournamentType(
        id: Long = 1L,
        name: String = "Tournament",
        description: String = "players start with a fixed amount of chips and play until one person wins all the chips."
    ):TournamentType{
        return TournamentType(
            id = id,
            name = name,
            description = description
        )
    }

    // Lager en standard User
    fun createUser(
        id: Long = 1L,
        username: String = "testuser",
        email: String = "testuser@example.com",
        isAdmin: Boolean = false,
        role: UserRole = createRole(),
        userDetail: UserDetail = createUserDetail()
    ): User {
        return User(
            id = id,
            username = username,
            email = email,
            isAdmin = isAdmin,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            role = role,
            userDetail = userDetail
        )
    }

    // Lager en standard Role
    fun createRole(id: Long = 1L, roleName: String = "USER"): UserRole {
        return UserRole(id = id, name = roleName, description = "Regular user")
    }

    fun createStatus(
        id: Long = 1L,
        name: String = "SCHEDULED",
        description: String = "Scheduled"
    ): Status {
        return Status(id = id, name = name, description = description)
    }

    // Lager en standard TournamentSpecification
    fun createTournamentSpecification(
        id: Long = 1L,
        buyIn: Double = 100.0,
        freeToPlay: Boolean = false
    ): TournamentSpecification {
        return TournamentSpecification(
            id = id,
            buyIn = buyIn,
            freeToPlay = freeToPlay,
            blindStructure = null,
            startingStack = null,
            tournamentType = createTournamentType()
        )
    }

    // Lager en standard Tournament
    fun createTournament(
        id: Long = 1L,
        tournamentName: String = "Test Tournament",
        user: User = createUser(),
        specification: TournamentSpecification = createTournamentSpecification(),
        status: Status = Status(id = 1L, name = "SCHEDULED", description = "Scheduled"),
        visibility: TournamentVisibility = TournamentVisibility.PUBLIC
    ): Tournament {
        return Tournament(
            id = id,
            tournamentName = tournamentName,
            description = "This is a test tournament",
            specification = specification,
            status = status,
            scheduledStartTime = LocalDateTime.now(),
            createdBy = user,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            visibility = visibility
        )
    }

    fun createUserCredential(
        id: Long = 1L,
        userId: Long,
        password: String = "testPassword123",
        isTemporal: Boolean = false,
        isActive: Boolean = true,
        validToDateTime: LocalDateTime? = LocalDateTime.now().plusDays(1L),
        createdAt : LocalDateTime = LocalDateTime.now(),
    ): UserCredential {
        return UserCredential(
            id = id,
            userId = userId,
            password = password,
            isTemporal = isTemporal,
            isActive = isActive,
            validToDateTime = validToDateTime,
            createdAt = createdAt
        )
    }
}

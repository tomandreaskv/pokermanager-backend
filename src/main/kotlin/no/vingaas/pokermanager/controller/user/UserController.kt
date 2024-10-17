package no.vingaas.pokermanager.controller.user

import no.vingaas.pokermanager.config.CustomUserDetails
import no.vingaas.pokermanager.dto.UserCreateDTO
import no.vingaas.pokermanager.dto.user.BasicUserDTO
import no.vingaas.pokermanager.dto.user.FullUserDTO
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.entities.user.UserCredential
import no.vingaas.pokermanager.entities.user.UserDetail
import no.vingaas.pokermanager.entities.user.UserRole
import no.vingaas.pokermanager.service.user.UserCredentialService
import no.vingaas.pokermanager.service.user.UserDetailService
import no.vingaas.pokermanager.service.user.UserRoleService
import no.vingaas.pokermanager.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val userDetailService: UserDetailService,
    private val userRoleService: UserRoleService,
    private val userCredentialService: UserCredentialService
) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    // Endepunkt for å hente bruker basert på ID
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long, authentication: Authentication): ResponseEntity<Any> {
        val currentUser = authentication.principal as CustomUserDetails
        val user = userService.findById(id) ?: return ResponseEntity.notFound().build()

        return if (currentUser.isAdmin) {
            // Returner full brukerinfo for admin
            val fullUserDTO = FullUserDTO(
                id = user.id,
                username = user.username,
                email = user.email,
                firstname = user.userDetail.firstname,
                lastname = user.userDetail.lastname,
                dateOfBirth = user.userDetail.dateOfBirth,
                country = user.userDetail.country,
                phoneNumber = user.userDetail.phoneNumber,
                address = user.userDetail.address,
                city = user.userDetail.city,
                zipCode = user.userDetail.zipCode,
                bio = user.userDetail.bio,
                profilePicture = user.userDetail.profilePicture,
                isAdmin = user.isAdmin,
                role = user.role.name
            )
            ResponseEntity.ok(fullUserDTO)
        } else {
            // Returner begrenset brukerinfo for vanlige brukere
            val basicUserDTO = BasicUserDTO(
                firstname = user.userDetail.firstname,
                lastname = user.userDetail.lastname,
                country = user.userDetail.country
            )
            ResponseEntity.ok(basicUserDTO)
        }
    }

    // Endepunkt for å hente alle brukere
    @GetMapping
    fun getAllUsers(authentication: Authentication): ResponseEntity<List<Any>> {
        val currentUser = authentication.principal as CustomUserDetails
        val users = userService.findAll()

        // Hvis brukeren er admin, returner full info, ellers returner begrenset info
        return if (currentUser.isAdmin) {
            ResponseEntity.ok(
                users.map { user ->
                    FullUserDTO(
                        id = user.id,
                        username = user.username,
                        email = user.email,
                        firstname = user.userDetail.firstname,
                        lastname = user.userDetail.lastname,
                        dateOfBirth = user.userDetail.dateOfBirth,
                        country = user.userDetail.country,
                        phoneNumber = user.userDetail.phoneNumber,
                        address = user.userDetail.address,
                        city = user.userDetail.city,
                        zipCode = user.userDetail.zipCode,
                        bio = user.userDetail.bio,
                        profilePicture = user.userDetail.profilePicture,
                        isAdmin = user.isAdmin,
                        role = user.role.name
                    )
                }
            )
        } else {
            ResponseEntity.ok(
                users.map { user ->
                    BasicUserDTO(
                        firstname = user.userDetail.firstname,
                        lastname = user.userDetail.lastname,
                        country = user.userDetail.country
                    )
                }
            )
        }
    }

    @PostMapping
    fun createUser(@RequestBody user: UserCreateDTO): ResponseEntity<User> {
        logger.info("Request for creating user: ${user.username}")
        val regularRole: UserRole = userRoleService.findByName("USER") ?: throw IllegalArgumentException("User role not found")
        val newUser = User(
            username = user.username,
            email = user.email,
            isAdmin = false,
            adminPermissions = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            userDetail = UserDetail(
                firstname = user.firstname,
                lastname = user.lastname,
                dateOfBirth = user.dateOfBirth,
                country = user.country,
                bio = null,
                address = null,
                city = null,
                phoneNumber = null,
                profilePicture = null,
                zipCode = null
            ),
            role = regularRole
        )
        val createdUser = userService.save(newUser)
        userCredentialService.save(
            UserCredential(
                userId = createdUser.id,
                password = user.password,
                isTemporal = false,
                isActive = true,
                validToDateTime = null,
                createdAt = LocalDateTime.now(),
            )
        )
        return ResponseEntity.ok(createdUser)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
        logger.info("Request for updating user with id: $id")
        val updatedUser = userService.update(user)
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        logger.info("Request for deleting user with id: $id")
        userService.delete(userService.findById(id)!!)
        return ResponseEntity.noContent().build()
    }
}
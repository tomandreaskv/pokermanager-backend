package no.vingaas.pokermanager.controller.user

import no.vingaas.pokermanager.dto.UserCreateDTO
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

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        logger.info("Request for getting user by id: $id")
        val user = userService.findById(id)
        return if (user != null) {
            logger.info("User with id $id found")
            ResponseEntity.ok(user)
        } else {
            logger.error("User with id $id not found")
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        logger.info("Request for getting all users")
        val users = userService.findAll()
        return ResponseEntity.ok(users)
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
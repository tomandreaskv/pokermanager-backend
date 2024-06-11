package no.vingaas.pokermanager.controller.user

import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.service.user.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class UserControllerTest {

    private var userService: UserService = mock(UserService::class.java)

    @InjectMocks
    private lateinit var userController: UserController

    private lateinit var user: User
    private lateinit var user2: User

    @BeforeEach
    fun setUp() {
        userController = UserController(userService)
        user = User(
            id = 1L,
            username = "testuser",
            email = "test@example.com",
            isAdmin = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            adminPermissions = listOf(),
            userDetail = mock()
        )

        user2 = User(
            id = 2L,
            username = "testuser2",
            email = "test2@example.com",
            isAdmin = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            adminPermissions = listOf(),
            userDetail = mock()
        )
    }

    @Test
    fun testGetUserById() {
        `when`(userService.findById(1L)).thenReturn(user)

        val response: ResponseEntity<User> = userController.getUserById(1L)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
    }

    @Test
    fun testGetUserByIdNotFound() {
        `when`(userService.findById(1L)).thenReturn(null)

        val response: ResponseEntity<User> = userController.getUserById(1L)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun testGetAllUsers() {
        val users = listOf(
            user,
        )
        `when`(userService.findAll()).thenReturn(users)

        val response: ResponseEntity<List<User>> = userController.getAllUsers()
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(users, response.body)
    }

    @Test
    fun testUpdateUser() {
        `when`(userService.update(user)).thenReturn(user)

        val response: ResponseEntity<User> = userController.updateUser(1L, user)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
    }

    @Test
    fun testDeleteUser() {
        `when`(userService.findById(1L)).thenReturn(user)
        doNothing().`when`(userService).delete(user)

        val response: ResponseEntity<Void> = userController.deleteUser(1L)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        verify(userService, times(1)).delete(user)
    }
}
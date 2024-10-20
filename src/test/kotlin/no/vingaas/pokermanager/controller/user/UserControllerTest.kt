package no.vingaas.pokermanager.controller.user

import no.vingaas.pokermanager.config.CustomUserDetails
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.service.user.UserCredentialService
import no.vingaas.pokermanager.service.user.UserDetailService
import no.vingaas.pokermanager.service.user.UserRoleService
import no.vingaas.pokermanager.service.user.UserService
import no.vingaas.pokermanager.util.TestDataFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication

@ExtendWith(MockitoExtension::class)
class UserControllerTest {

    private var userService: UserService = mock(UserService::class.java)
    private var userDetailService: UserDetailService = mock(UserDetailService::class.java)
    private var userRoleService: UserRoleService = mock(UserRoleService::class.java)
    private var userCredentialService: UserCredentialService = mock(UserCredentialService::class.java)

    @InjectMocks
    private lateinit var userController: UserController

    private lateinit var user: User
    private lateinit var authentication: Authentication
    private lateinit var customUserDetails: CustomUserDetails

    @BeforeEach
    fun setUp() {
        userController = UserController(userService, userDetailService, userRoleService, userCredentialService)

        // Bruk TestDataFactory til å opprette testdata
        user = TestDataFactory.createUser(username = "testuser", email = "test@example.com", isAdmin = false)

        // Mock `Authentication` og `CustomUserDetails`
        authentication = mock(Authentication::class.java)
        customUserDetails = mock(CustomUserDetails::class.java)

        `when`(authentication.principal).thenReturn(customUserDetails)
        `when`(customUserDetails.isAdmin).thenReturn(true)  // Simulerer en admin-bruker
    }

    @Test
    fun testGetUserById() {
        `when`(userService.findById(1L)).thenReturn(user)
        `when`(customUserDetails.isAdmin).thenReturn(true)  // Simuler adminbruker

        val response: ResponseEntity<Any> = userController.getUserById(1L, authentication)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
    }

    @Test
    fun testGetUserByIdNotFound() {
        `when`(userService.findById(1L)).thenReturn(null)

        val response: ResponseEntity<Any> = userController.getUserById(1L, authentication)
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun testGetAllUsers() {
        val users = listOf(user)
        `when`(userService.findAll()).thenReturn(users)

        val response: ResponseEntity<List<Any>> = userController.getAllUsers(authentication)
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
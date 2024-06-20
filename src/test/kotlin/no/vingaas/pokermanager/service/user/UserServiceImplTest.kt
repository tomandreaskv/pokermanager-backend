package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.exception.user.InvalidUserException
import no.vingaas.pokermanager.repository.user.UserRepository
import no.vingaas.pokermanager.validator.user.UserValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserServiceImplTest {
    private var userRepository: UserRepository = mock(UserRepository::class.java)

    private  var validator: UserValidator = mock(UserValidator::class.java)

    @InjectMocks
    private lateinit var userService: UserServiceImpl

    private lateinit var user: User

    @BeforeEach
    fun setUp() {
        user = User(
            id = 1L,
            username = "testuser",
            email = "test@example.com",
            isAdmin = false,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            adminPermissions = listOf(),
            userDetail = mock(),
            role = mock()
        )

    }

    @Test
    fun `test findByUsername`() {
        `when`(userRepository.findByUsername("testuser")).thenReturn(user)

        val found = userService.findByUsername("testuser")

        assertEquals("testuser", found?.username)
        verify(userRepository, times(1)).findByUsername("testuser")
    }

    @Test
    fun `test findByEmail`() {
        `when`(userRepository.findByEmail("test@example.com")).thenReturn(user)

        val found = userService.findByEmail("test@example.com")

        assertEquals("test@example.com", found?.email)
        verify(userRepository, times(1)).findByEmail("test@example.com")
    }

    @Test
    fun `test existsByUsername`() {
        `when`(userRepository.existsByUsername("testuser")).thenReturn(true)

        val exists = userService.existsByUsername("testuser")

        assertTrue(exists)
        verify(userRepository, times(1)).existsByUsername("testuser")
    }

    @Test
    fun `test existsByEmail`() {
        `when`(userRepository.existsByEmail("test@example.com")).thenReturn(true)

        val exists = userService.existsByEmail("test@example.com")

        assertTrue(exists)
        verify(userRepository, times(1)).existsByEmail("test@example.com")
    }

    @Test
    fun `test save valid user`() {
        `when`(userRepository.save(user)).thenReturn(user)

        val saved = userService.save(user)

        assertEquals("testuser", saved.username)
        verify(userRepository, times(1)).save(user)
    }

    @Test
    fun `test save invalid user`() {
        `when`(validator.validate(user)).thenReturn(setOf("Invalid username"))

        val exception = assertThrows(InvalidUserException::class.java) {
            userService.save(user)
        }

        assertEquals("Invalid user data: Invalid username", exception.message)
        verify(userRepository, never()).save(any())
    }

    @Test
    fun `test update valid user`() {
        `when`(userRepository.save(user)).thenReturn(user)

        val updated = userService.update(user)

        assertEquals("testuser", updated.username)
        verify(userRepository, times(1)).save(user)
    }

    @Test
    fun `test update invalid user`() {
        `when`(validator.validate(user)).thenReturn(setOf("Invalid username"))

        val exception = assertThrows(InvalidUserException::class.java) {
            userService.update(user)
        }

        assertEquals("Invalid user data: Invalid username", exception.message)
        verify(userRepository, never()).save(any())
    }

    @Test
    fun `test delete user`() {
        doNothing().`when`(userRepository).delete(any())

        userService.delete(user)

        verify(userRepository, times(1)).delete(user)
    }

    @Test
    fun `test findAll`() {
        `when`(userRepository.findAllUsers()).thenReturn(listOf(user))

        val users = userService.findAll()

        assertEquals(1, users.size)
        verify(userRepository, times(1)).findAllUsers()
    }

    @Test
    fun `test findById`() {
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(user))

        val found = userService.findById(1L)

        assertNotNull(found)
        assertEquals("testuser", found?.username)
        verify(userRepository, times(1)).findById(1L)
    }
}

package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.entities.user.UserCredential
import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserCredentialServiceImplTest {

    @Mock
    private lateinit var userCredentialRepository: UserCredentialRepository

    @InjectMocks
    private lateinit var userCredentialService: UserCredentialServiceImpl

   private val user = User(
    id = 1L,
    username = "testuser",
    email = "test@example.com",
    isAdmin = false,
    createdAt = LocalDateTime.now(),
    updatedAt = LocalDateTime.now(),
    adminPermissions = listOf(),
    userDetail = mock()
    )

    private val userCredential = UserCredential(1L, user, "test")

    @Test
    fun `should return user credential when id exists`() {
        val userCredential = userCredential
        val id = 1L
        Mockito.`when`(userCredentialRepository.findById(id)).thenReturn(Optional.of(userCredential))

        val result = userCredentialService.getCredentialsById(id)

        assertEquals(userCredential, result)
    }

    @Test
    fun `should throw exception when id does not exist`() {
        val id = 1L
        Mockito.`when`(userCredentialRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows<IllegalArgumentException> {
            userCredentialService.getCredentialsById(id)
        }
    }

    @Test
    fun `should return user credential when username exists`() {
        val userCredential = userCredential
        val username = userCredential.user.username
        Mockito.`when`(userCredentialRepository.findByUserUsername(username)).thenReturn(userCredential)

        val result = userCredentialService.getCredentialsByUsername(username)

        assertEquals(userCredential, result)
    }

    @Test
    fun `should throw exception when username does not exist`() {
        val username = "test"
        Mockito.`when`(userCredentialRepository.findByUserUsername(username)).thenReturn(null)

        assertThrows<IllegalArgumentException> {
            userCredentialService.getCredentialsByUsername(username)
        }
    }

    @Test
    fun `should save and return user credential`() {
        val userCredential = UserCredential(1L, user, "test")
        Mockito.`when`(userCredentialRepository.save(userCredential)).thenReturn(userCredential)

        val result = userCredentialService.save(userCredential)

        assertEquals(userCredential, result)
    }

    @Test
    fun `should update and return user credential`() {
        val userCredential = userCredential
        Mockito.`when`(userCredentialRepository.save(userCredential)).thenReturn(userCredential)

        val result = userCredentialService.update(userCredential)

        assertEquals(userCredential, result)
    }

    @Test
    fun `should delete user credential`() {
        val userCredential = userCredential

        Mockito.doNothing().`when`(userCredentialRepository).delete(userCredential)

        userCredentialService.delete(userCredential)

        Mockito.verify(userCredentialRepository, Mockito.times(1)).delete(userCredential)
    }
}
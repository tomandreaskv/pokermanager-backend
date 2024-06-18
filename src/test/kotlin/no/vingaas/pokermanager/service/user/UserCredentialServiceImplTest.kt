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

    @Mock
    private lateinit var userService : UserServiceImpl

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

    private val userCredential = UserCredential(id = 1L, userId = 1L, password = "test", isTemporal = false, isActive = true, validToDateTime = LocalDateTime.of(2025,1,1,1,1), createdAt = LocalDateTime.now())

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
        val userid = userCredential.userId
        Mockito.`when`(userCredentialRepository.findByUserId(userid)).thenReturn(Optional.of(userCredential))

        val result = userCredentialService.getCredentialsByUserId(userid).get()

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

        Mockito.doNothing().`when`(userCredentialRepository).deleteById(userCredential.id)

        userCredentialService.delete(userCredential.id)

        Mockito.verify(userCredentialRepository, Mockito.times(1)).deleteById(userCredential.id)
    }
}
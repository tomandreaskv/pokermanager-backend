package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.repository.user.UserCredentialRepository
import no.vingaas.pokermanager.util.TestDataFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserCredentialServiceImplTest {

    private lateinit var credentialRepository: UserCredentialRepository
    private lateinit var userService : UserService

    @InjectMocks
    private lateinit var credentialService: UserCredentialServiceImpl

    @BeforeEach
    fun setUp() {
        credentialRepository = mock(UserCredentialRepository::class.java)
        userService = mock(UserServiceImpl::class.java)
        credentialService = UserCredentialServiceImpl(credentialRepository, userService)
    }

    @Test
    fun `should return user credential when id exists`() {
        val userCredential = TestDataFactory.createUserCredential(userId = 1L)

        `when`(credentialRepository.findById(userCredential.id)).thenReturn(Optional.of(userCredential))

        val result = credentialService.getCredentialsById(userCredential.id)

        assertEquals(userCredential, result)
    }

    @Test
    fun `should throw exception when id does not exist`() {
        val userCredentialId = 1L

        `when`(credentialRepository.findById(userCredentialId)).thenReturn(Optional.empty())

        assertThrows<IllegalArgumentException> {
            credentialService.getCredentialsById(userCredentialId)
        }
    }

    @Test
    fun `should update and return user credential`() {
        val userCredential = TestDataFactory.createUserCredential(userId = 1L)

        `when`(credentialRepository.save(userCredential)).thenReturn(userCredential)

        val result = credentialService.update(userCredential)

        assertEquals(userCredential, result)
    }

    @Test
    fun `should delete user credential`() {
        val userCredential = TestDataFactory.createUserCredential(userId = 1L)

        doNothing().`when`(credentialRepository).deleteById(userCredential.id)

        credentialService.delete(userCredential.id)

        verify(credentialRepository, times(1)).deleteById(userCredential.id)
    }
}
package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserDetail
import no.vingaas.pokermanager.repository.user.UserDetailRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserDetailServiceImplTest {

    @Mock
    private lateinit var userDetailRepository: UserDetailRepository

    @InjectMocks
    private lateinit var userDetailService: UserDetailServiceImpl

    private  val userDetail = UserDetail(id = 1L, address = "Test address", city = "Test city", country = "Test country", zipCode = "1234", bio = "Test bio", firstname = "Test firstname", lastname = "Test lastname", profilePicture = "Test profile picture", dateOfBirth = LocalDate.of(1995,2,13), phoneNumber = "12345678")

    @Test
    fun `should return user detail when id exists`() {

        val id = 1L
        Mockito.`when`(userDetailRepository.findById(id)).thenReturn(Optional.of(userDetail))

        val result = userDetailService.getDetailsById(id)

        assertEquals(userDetail, result)
    }

    @Test
    fun `should throw exception when id does not exist`() {
        val id = 1L
        Mockito.`when`(userDetailRepository.findById(id)).thenReturn(Optional.empty())

        assertThrows<IllegalArgumentException> {
            userDetailService.getDetailsById(id)
        }
    }

    @Test
    fun `should save and return user detail`() {
        val userDetail = userDetail
        Mockito.`when`(userDetailRepository.save(userDetail)).thenReturn(userDetail)

        val result = userDetailService.save(userDetail)

        assertEquals(userDetail, result)
    }

    @Test
    fun `should update and return user detail`() {
        val userDetail = userDetail
        Mockito.`when`(userDetailRepository.save(userDetail)).thenReturn(userDetail)

        val result = userDetailService.update(userDetail)

        assertEquals(userDetail, result)
    }

    @Test
    fun `should delete user detail`() {
        val userDetail = userDetail

        Mockito.doNothing().`when`(userDetailRepository).delete(userDetail)

        userDetailService.delete(userDetail)

        Mockito.verify(userDetailRepository, Mockito.times(1)).delete(userDetail)
    }
}
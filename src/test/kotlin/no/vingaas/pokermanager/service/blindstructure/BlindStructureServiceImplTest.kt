package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindLevel
import no.vingaas.pokermanager.entities.blindstructure.BlindStructure
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.repository.blindstructure.BlindStructureRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class BlindStructureServiceImplTest{
    @Mock
    private lateinit var blindStructureRepository: BlindStructureRepository

    @InjectMocks
    private lateinit var blindStructureServiceImpl: BlindStructureServiceImpl

    private val dummyUser = User(
        id = 1L,
        username = "testuser",
        email = "test@example.com",
        isAdmin = false,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        adminPermissions = listOf(),
        userDetail = Mockito.mock()
    )
    private val dummyBlindStructure = BlindStructure(
        id = 1,
        name = "Test Structure",
        levels = listOf(
            BlindLevel(id = 1, blindStructureId = 1, levelOrder = 1, duration = 10, smallBlind = 10, bigBlind = 20, ante = 5)
        ),
        createdBy = dummyUser,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )


    @Test
    fun `test getBlindStructureWithId success`() {
        `when`(blindStructureRepository.findById(1L)).thenReturn(Optional.of(dummyBlindStructure))

        val result = blindStructureServiceImpl.getBlindStructureWithId(1L)

        assertEquals(dummyBlindStructure, result)
        verify(blindStructureRepository).findById(1L)
    }

    @Test
    fun `test getBlindStructureWithId not found`() {
        `when`(blindStructureRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            blindStructureServiceImpl.getBlindStructureWithId(1L)
        }

        assertEquals("No BlindStructure with id: 1", exception.message)
        verify(blindStructureRepository).findById(1L)
    }

    @Test
    fun `test createBlindStructure success`() {
        val blindStructureCaptor = ArgumentCaptor.forClass(BlindStructure::class.java)
        `when`(blindStructureRepository.save(Mockito.any(BlindStructure::class.java))).thenReturn(dummyBlindStructure)

        val result = blindStructureServiceImpl.createBlindStructure(dummyBlindStructure, dummyUser)

        verify(blindStructureRepository).save(blindStructureCaptor.capture())
        val capturedBlindStructure = blindStructureCaptor.value
        assertEquals("Test Structure", capturedBlindStructure.name)
        assertEquals(dummyUser, capturedBlindStructure.createdBy)
        assertEquals(dummyBlindStructure, result)
    }

    @Test
    fun `test updateBlindStructure success`() {
        `when`(blindStructureRepository.findById(1L)).thenReturn(Optional.of(dummyBlindStructure))
        `when`(blindStructureRepository.save(Mockito.any(BlindStructure::class.java))).thenReturn(dummyBlindStructure)

        val result = blindStructureServiceImpl.updateBlindStructure(dummyBlindStructure)

        assertEquals(dummyBlindStructure, result)
        verify(blindStructureRepository).findById(1L)
        verify(blindStructureRepository).save(dummyBlindStructure)
    }

    @Test
    fun `test updateBlindStructure not found`() {
        `when`(blindStructureRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            blindStructureServiceImpl.updateBlindStructure(dummyBlindStructure)
        }

        assertEquals("No BlindStructure with id: 1", exception.message)
        verify(blindStructureRepository).findById(1L)
    }

    @Test
    fun `test deleteBlindStructure success`() {
        `when`(blindStructureRepository.existsById(1L)).thenReturn(true)

        blindStructureServiceImpl.deleteBlindStructure(1L)

        verify(blindStructureRepository).existsById(1L)
        verify(blindStructureRepository).deleteById(1L)
    }

    @Test
    fun `test deleteBlindStructure not found`() {
        `when`(blindStructureRepository.existsById(1L)).thenReturn(false)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            blindStructureServiceImpl.deleteBlindStructure(1L)
        }

        assertEquals("No BlindStructure with id: 1", exception.message)
        verify(blindStructureRepository).existsById(1L)
    }

    @Test
    fun `test getAllBlindStructures success`() {
        `when`(blindStructureRepository.findAll()).thenReturn(listOf(dummyBlindStructure))

        val result = blindStructureServiceImpl.getAllBlindStructures()

        assertEquals(listOf(dummyBlindStructure), result)
        verify(blindStructureRepository).findAll()
    }
}
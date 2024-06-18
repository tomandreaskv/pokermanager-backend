package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.dto.blindstructure.BlindLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.BreakLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.CreateLevelDTO
import no.vingaas.pokermanager.entities.blindstructure.BlindLevel
import no.vingaas.pokermanager.entities.blindstructure.BreakLevel
import no.vingaas.pokermanager.repository.blindstructure.LevelRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class LevelServiceImplTest {

    @Mock
    private lateinit var levelRepository: LevelRepository

    @InjectMocks
    private lateinit var levelServiceImpl: LevelServiceImpl

    private val dummyBlindLevel = BlindLevel(
        id = 1L,
        blindStructureId = 1L,
        levelOrder = 1,
        duration = 10,
        smallBlind = 10,
        bigBlind = 20,
        ante = 5
    )
    private val dummyBreakLevel = BreakLevel(
        id = 2L,
        blindStructureId = 1L,
        levelOrder = 2,
        duration = 5,
        colorUp = true
    )
    private val dummyBlindLevelDTO = BlindLevelDTO(
        id = 1L,
        blindStructureId = 1L,
        levelOrder = 1,
        duration = 10,
        smallBlind = 10,
        bigBlind = 20,
        ante = 5
    )
    private val dummyBreakLevelDTO = BreakLevelDTO(
        id = 2L,
        blindStructureId = 1L,
        levelOrder = 2,
        duration = 5,
        colorUp = true
    )
    private val createBlindLevelDTO = CreateLevelDTO(
        type = "blind",
        blindStructureId = 1L,
        level = 1,
        duration = 10,
        smallBlind = 10,
        bigBlind = 20,
        ante = 5,
        colorUp = null
    )
    private val createBreakLevelDTO = CreateLevelDTO(
        type = "break",
        blindStructureId = 1L,
        level = 2,
        duration = 5,
        smallBlind = null,
        bigBlind = null,
        ante = null,
        colorUp = true
    )

    @AfterEach
    fun tearDown() {
        reset(levelRepository)
    }

    @Test
    fun `test getLevelWithId success`() {
        `when`(levelRepository.findById(1L)).thenReturn(Optional.of(dummyBlindLevel))

        val result = levelServiceImpl.getLevelWithId(1L)

        assertEquals(dummyBlindLevel, result)
        verify(levelRepository).findById(1L)
    }

    @Test
    fun `test getLevelWithId not found`() {
        `when`(levelRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            levelServiceImpl.getLevelWithId(1L)
        }

        assertEquals("No Level with id: 1", exception.message)
        verify(levelRepository).findById(1L)
    }

    @Test
    fun `test getAllLevels success`() {
        `when`(levelRepository.findAll()).thenReturn(listOf(dummyBlindLevel, dummyBreakLevel))

        val result = levelServiceImpl.getAllLevels()

        assertEquals(listOf(dummyBlindLevel, dummyBreakLevel), result)
        verify(levelRepository).findAll()
    }

    @Test
    fun `test createBlindLevel success`() {
        val levelCaptor = ArgumentCaptor.forClass(BlindLevel::class.java)
        `when`(levelRepository.save(Mockito.any(BlindLevel::class.java))).thenReturn(dummyBlindLevel)

        val result = levelServiceImpl.createLevel(dummyBlindLevel)

        verify(levelRepository).save(levelCaptor.capture())
        val capturedLevel = levelCaptor.value
        assertEquals(dummyBlindLevel.blindStructureId, capturedLevel.blindStructureId)
        assertEquals(dummyBlindLevel.levelOrder, capturedLevel.levelOrder)
        assertEquals(dummyBlindLevel.smallBlind, capturedLevel.smallBlind)
        assertEquals(dummyBlindLevel.bigBlind, capturedLevel.bigBlind)
        assertEquals(dummyBlindLevel.ante, capturedLevel.ante)
        assertEquals(dummyBlindLevel.duration, capturedLevel.duration)
        assertEquals(dummyBlindLevel, result)
    }

    @Test
    fun `test createBreakLevel success`() {
        val levelCaptor = ArgumentCaptor.forClass(BreakLevel::class.java)
        `when`(levelRepository.save(Mockito.any(BreakLevel::class.java))).thenReturn(dummyBreakLevel)

        var result = levelServiceImpl.createLevel(dummyBreakLevel)

        verify(levelRepository).save(levelCaptor.capture())
        val capturedLevel = levelCaptor.value
        assertEquals(dummyBreakLevel.blindStructureId, capturedLevel.blindStructureId)
        assertEquals(dummyBreakLevel.levelOrder, capturedLevel.levelOrder)
        assertEquals(dummyBreakLevel.colorUp, capturedLevel.colorUp)
        assertEquals(dummyBreakLevel.duration, capturedLevel.duration)
        assertEquals(dummyBreakLevel, result)
    }

    @Test
    fun `test updateBlindLevel success`() {
        `when`(levelRepository.findById(1L)).thenReturn(Optional.of(dummyBlindLevel))
        `when`(levelRepository.save(Mockito.any(BlindLevel::class.java))).thenReturn(dummyBlindLevel)

        val result = levelServiceImpl.updateLevel(dummyBlindLevel)

        assertEquals(dummyBlindLevel, result)
        verify(levelRepository).findById(1L)
        verify(levelRepository).save(dummyBlindLevel)
    }

    @Test
    fun `test updateBreakLevel success`() {
        `when`(levelRepository.findById(2L)).thenReturn(Optional.of(dummyBreakLevel))
        `when`(levelRepository.save(Mockito.any(BreakLevel::class.java))).thenReturn(dummyBreakLevel)

        val result = levelServiceImpl.updateLevel(dummyBreakLevel)

        assertEquals(dummyBreakLevel, result)
        verify(levelRepository).findById(2L)
        verify(levelRepository).save(dummyBreakLevel)
    }

    @Test
    fun `test updateLevel not found`() {
        `when`(levelRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows(IllegalArgumentException::class.java) {
            levelServiceImpl.updateLevel(dummyBlindLevel)
        }

        assertEquals("No Level with id: 1", exception.message)
        verify(levelRepository).findById(1L)
    }

    @Test
    fun `test deleteLevel success`() {
        `when`(levelRepository.existsById(1L)).thenReturn(true)

        levelServiceImpl.deleteLevel(1L)

        verify(levelRepository).existsById(1L)
        verify(levelRepository).deleteById(1L)
    }

    @Test
    fun `test deleteLevel not found`() {
        `when`(levelRepository.existsById(1L)).thenReturn(false)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            levelServiceImpl.deleteLevel(1L)
        }

        assertEquals("No Level with id: 1", exception.message)
        verify(levelRepository).existsById(1L)
    }
}
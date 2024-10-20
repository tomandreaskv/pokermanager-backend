package no.vingaas.pokermanager.service.tournament

import no.vingaas.pokermanager.dto.tournament.CreateTournamentDTO
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.tournament.TournamentVisibility
import no.vingaas.pokermanager.repository.blindstructure.BlindStructureRepository
import no.vingaas.pokermanager.repository.common.StatusRepository
import no.vingaas.pokermanager.repository.stack.StartingStackRepository
import no.vingaas.pokermanager.repository.tournament.TournamentRepository
import no.vingaas.pokermanager.repository.tournament.TournamentTypeRepository
import no.vingaas.pokermanager.util.TestDataFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class TournamentServiceImplTest {
    // Mock alle repositories
    @Mock
    private lateinit var tournamentRepository: TournamentRepository

    @Mock
    private lateinit var tournamentTypeRepository: TournamentTypeRepository

    @Mock
    private lateinit var statusRepository: StatusRepository

    @Mock
    private lateinit var blindStructureRepository: BlindStructureRepository

    @Mock
    private lateinit var startingStackRepository: StartingStackRepository

    @InjectMocks
    private lateinit var tournamentService: TournamentServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should create a tournament successfully`() {
        val createTournamentDTO = CreateTournamentDTO(
            tournamentName = "Test Tournament",
            buyIn = 100.0,
            blindStructureId = null,
            description = "Test description",
            freeToPlay = false,
            scheduledStartTime = null,  // Dette skal trigge "DRAFT" status
            startingStackId = null,
            tournamentTypeId = 1L,
            visibility = TournamentVisibility.PUBLIC
        )

        val user = TestDataFactory.createUser()

        // Mock status lookup for DRAFT status (since scheduledStartTime is null)
        val draftStatus = TestDataFactory.createStatus(id = 1L, name = "DRAFT", description = "Draft status")
        `when`(statusRepository.findByName("DRAFT")).thenReturn(Optional.of(draftStatus))

        // Mock repository save behavior
        `when`(tournamentRepository.save(any(Tournament::class.java)))
            .thenAnswer { invocation -> invocation.getArgument(0) }

        // Kall til service for å opprette turneringen
        val createdTournament = tournamentService.createTournament(createTournamentDTO, user)

        // Assert at turneringen ble opprettet med riktig status og verdier
        assertNotNull(createdTournament)
        assertEquals("Test Tournament", createdTournament.tournamentName)
        assertEquals(100.0, createdTournament.specification.buyIn)
        assertEquals(user, createdTournament.createdBy)
        assertEquals(draftStatus, createdTournament.status)  // Her sjekker vi at status er DRAFT

        // Verifiser at mocks ble kalt
        verify(statusRepository, times(1)).findByName("DRAFT")
        verify(tournamentRepository, times(1)).save(any(Tournament::class.java))
    }


    @Test
    fun `should return tournament by id`() {
        val tournament = TestDataFactory.createTournament()

        `when`(tournamentRepository.findById(1L)).thenReturn(Optional.of(tournament))

        val foundTournament = tournamentService.getTournamentById(1L)

        assertNotNull(foundTournament)
        assertEquals("Test Tournament", foundTournament?.tournamentName)
        assertEquals(100.0, foundTournament?.specification?.buyIn)

        verify(tournamentRepository, times(1)).findById(1L)
    }
}
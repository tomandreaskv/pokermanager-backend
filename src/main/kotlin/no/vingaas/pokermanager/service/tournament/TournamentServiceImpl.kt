package no.vingaas.pokermanager.service.tournament

import no.vingaas.pokermanager.dto.tournament.CreateTournamentDTO
import no.vingaas.pokermanager.dto.tournament.UpdateTournamentDTO
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.tournament.TournamentSpecification
import no.vingaas.pokermanager.entities.tournament.TournamentVisibility
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.exception.user.NoSuchTournamentTypeException
import no.vingaas.pokermanager.repository.blindstructure.BlindStructureRepository
import no.vingaas.pokermanager.repository.common.StatusRepository
import no.vingaas.pokermanager.repository.stack.StartingStackRepository
import no.vingaas.pokermanager.repository.tournament.TournamentRepository
import no.vingaas.pokermanager.repository.tournament.TournamentTypeRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TournamentServiceImpl(
    private val tournamentRepository: TournamentRepository,
    private val tournamentTypeRepository: TournamentTypeRepository,
    private val statusRepository: StatusRepository,
    private val blindStructureRepository: BlindStructureRepository,
    private val startingStackRepository: StartingStackRepository

): TournamentService {
    private val logger = LoggerFactory.getLogger(TournamentServiceImpl::class.java)

    override fun createTournament(createTournamentDTO: CreateTournamentDTO, createdBy: User): Tournament {
        // Hvis nødvendig informasjon mangler, sett turneringen til DRAFT-status
        val status = if (createTournamentDTO.scheduledStartTime == null || createTournamentDTO.buyIn == null) {
            statusRepository.findByName("DRAFT").orElseThrow { IllegalArgumentException("DRAFT status not found") }
        } else {
            statusRepository.findByName("SCHEDULED").orElseThrow { IllegalArgumentException("SCHEDULED status not found") }
        }

        // Hent TournamentType hvis id er gitt
        val tournamentType = createTournamentDTO.tournamentTypeId?.let {
            tournamentTypeRepository.findById(it).orElseThrow {
                NoSuchTournamentTypeException("Tournament type with ID ${createTournamentDTO.tournamentTypeId} does not exist.")
            }
        }

        // Bygg TournamentSpecification
        val tournamentSpecification = TournamentSpecification(
            buyIn = createTournamentDTO.buyIn ?: 0.0,  // Standardverdi hvis ikke oppgitt
            blindStructure = createTournamentDTO.blindStructureId?.let { blindStructureRepository.findById(it).orElse(null) },
            startingStack = createTournamentDTO.startingStackId?.let { startingStackRepository.findById(it).orElse(null) },
            freeToPlay = createTournamentDTO.freeToPlay ?: false,  // Set standardverdi hvis ikke oppgitt
            tournamentType = tournamentType ?: throw IllegalArgumentException("TournamentType is required")
        )

        // Bygg Tournament med den opprettede spesifikasjonen
        val tournament = Tournament(
            tournamentName = createTournamentDTO.tournamentName,
            description = createTournamentDTO.description,
            specification = tournamentSpecification,
            status = status,
            scheduledStartTime = createTournamentDTO.scheduledStartTime,
            createdBy = createdBy,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            visibility = createTournamentDTO.visibility
        )

        // Lagre turneringen i databasen
        return tournamentRepository.save(tournament)
    }

    override fun getTournamentById(id: Long): Tournament? {
        logger.info("Fetching tournament with id: $id")
        return tournamentRepository.findById(id).orElse(null)
    }

    override fun getAllTournaments(): List<Tournament> {
        logger.info("Fetching all tournaments")
        return tournamentRepository.findAll()
    }

    override fun getTournamentsByUser(user: User): List<Tournament> {
        logger.info("Fetching tournaments for user: ${user.username}")
        return tournamentRepository.findByParticipantsContainsOrCreatedBy(user, user)
    }

    override fun updateTournament(id: Long, updateTournamentDTO: UpdateTournamentDTO, updatedBy: User): Tournament {
        logger.info("Updating tournament with id: $id by user: ${updatedBy.username}")
        val tournament = tournamentRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Tournament not found with id: $id") }

        val updatedTournament = tournament.copy(
            tournamentName = updateTournamentDTO.tournamentName ?: tournament.tournamentName,
            description = updateTournamentDTO.description ?: tournament.description,
            specification = updateTournamentDTO.specification ?: tournament.specification,
            scheduledStartTime = updateTournamentDTO.scheduledStartTime ?: tournament.scheduledStartTime,
            updatedAt = LocalDateTime.now(),
            visibility = updateTournamentDTO.visibility ?: tournament.visibility
        )

        logger.info("Tournament updated successfully: $id")
        return tournamentRepository.save(updatedTournament)
    }

    override fun deleteTournament(id: Long): Boolean {
        logger.info("Deleting tournament with id: $id")
        tournamentRepository.deleteById(id)
        logger.info("Tournament deleted successfully: $id")
        return true
    }

    override fun getAccessibleTournaments(user: User): List<Tournament> {
        return if (user.isAdmin) {
            // Admin kan se alle turneringer
            tournamentRepository.findAll()
        } else {
            // Vanlige brukere kan kun se offentlige, deltakede, inviterte eller opprettede turneringer
            tournamentRepository.findByVisibilityOrParticipantsContainsOrCreatedBy(
                TournamentVisibility.PUBLIC, user
            )
        }
    }
}
package no.vingaas.pokermanager.service.tournament

import no.vingaas.pokermanager.dto.tournament.CreateTournamentDTO
import no.vingaas.pokermanager.dto.tournament.UpdateTournamentDTO
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.tournament.TournamentVisibility
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.repository.tournament.TournamentRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TournamentServiceImpl(
    private val tournamentRepository: TournamentRepository
): TournamentService {
    private val logger = LoggerFactory.getLogger(TournamentServiceImpl::class.java)

    override fun createTournament(createTournamentDTO: CreateTournamentDTO, createdBy: User): Tournament {
        val tournament = Tournament(
            tournamentName = createTournamentDTO.tournamentName,
            description = createTournamentDTO.description,
            specification = createTournamentDTO.specification,
            status = createTournamentDTO.status,
            scheduledStartTime = createTournamentDTO.scheduledStartTime,
            createdBy = createdBy,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            visibility = createTournamentDTO.visibility
        )

        logger.info("Creating new tournament: ${createTournamentDTO.tournamentName}")
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
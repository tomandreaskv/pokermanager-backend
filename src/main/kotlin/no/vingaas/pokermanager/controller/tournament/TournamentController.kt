package no.vingaas.pokermanager.controller.tournament

import no.vingaas.pokermanager.dto.tournament.CreateTournamentDTO
import no.vingaas.pokermanager.dto.tournament.UpdateTournamentDTO
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.service.tournament.TournamentService
import no.vingaas.pokermanager.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/tournaments")
class TournamentController (
    private val tournamentService: TournamentService,
    private val userService: UserService  // For å hente brukeren som er autentisert
) {
    private val logger = LoggerFactory.getLogger(TournamentController::class.java)

    // Opprettelse av ny turnering
    @PostMapping
    fun createTournament(
        @RequestBody createTournamentDTO: CreateTournamentDTO,
        authentication: Authentication  // Dette henter innlogget bruker
    ): ResponseEntity<Tournament> {
        logger.info("Request to create tournament: ${createTournamentDTO.tournamentName}")

        val currentUser = userService.getUserByEmail(authentication.name)  // Få den autentiserte brukeren
        val createdTournament = tournamentService.createTournament(createTournamentDTO, currentUser)

        return ResponseEntity.created(URI.create("/api/tournaments/${createdTournament.id}")).body(createdTournament)
    }

    // Hent alle turneringer
    @GetMapping
    fun getAccessibleTournaments(authentication: Authentication): ResponseEntity<List<Tournament>> {
        val currentUser = userService.getUserByEmail(authentication.name)
        val accessibleTournaments = tournamentService.getAccessibleTournaments(currentUser)
        return ResponseEntity.ok(accessibleTournaments)
    }

    // Hent en spesifikk turnering ved ID
    @GetMapping("/{id}")
    fun getTournamentById(@PathVariable id: Long): ResponseEntity<Tournament> {
        logger.info("Request to fetch tournament with id: $id")
        val tournament = tournamentService.getTournamentById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(tournament)
    }

    // Oppdater en eksisterende turnering
    @PutMapping("/{id}")
    fun updateTournament(
        @PathVariable id: Long,
        @RequestBody updateTournamentDTO: UpdateTournamentDTO,
        authentication: Authentication
    ): ResponseEntity<Tournament> {
        logger.info("Request to update tournament with id: $id")

        val currentUser = userService.getUserByEmail(authentication.name)
        val updatedTournament = tournamentService.updateTournament(id, updateTournamentDTO, currentUser)

        return ResponseEntity.ok(updatedTournament)
    }

    // Slett en turnering (valgfritt)
    @DeleteMapping("/{id}")
    fun deleteTournament(@PathVariable id: Long): ResponseEntity<Void> {
        logger.info("Request to delete tournament with id: $id")
        val deleted = tournamentService.deleteTournament(id)
        return if (deleted) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }
}
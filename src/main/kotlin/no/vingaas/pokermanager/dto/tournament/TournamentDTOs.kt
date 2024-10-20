package no.vingaas.pokermanager.dto.tournament

import no.vingaas.pokermanager.entities.tournament.TournamentSpecification
import no.vingaas.pokermanager.entities.tournament.TournamentVisibility
import java.time.LocalDateTime

data class CreateTournamentDTO(
    val tournamentName: String,
    val description: String?,
    val buyIn: Double?,
    val blindStructureId: Long?,  // Valgfritt, men refererer til eksisterende BlindStructure
    val startingStackId: Long?,  // Valgfritt, men refererer til eksisterende StartingStack
    val freeToPlay: Boolean?,  // Valgfritt, om turneringen er gratis
    val tournamentTypeId: Long?,  // Turneringstype må være tilgjengelig eller opprettes
    val scheduledStartTime: LocalDateTime?,  // Valgfritt for DRAFT status
    val visibility: TournamentVisibility  // Synlighet (PUBLIC, PRIVATE, osv.)
)

data class UpdateTournamentDTO(
    val tournamentName: String?,
    val description: String?,
    val specification: TournamentSpecification?,
    val scheduledStartTime: LocalDateTime?,
    val visibility: TournamentVisibility?
)
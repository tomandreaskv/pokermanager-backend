package no.vingaas.pokermanager.dto.tournament

import no.vingaas.pokermanager.entities.common.Status
import no.vingaas.pokermanager.entities.tournament.TournamentSpecification
import no.vingaas.pokermanager.entities.tournament.TournamentVisibility
import java.time.LocalDateTime

data class CreateTournamentDTO(
    val tournamentName: String,
    val description: String?,
    val specification: TournamentSpecification,
    val status: Status,
    val scheduledStartTime: LocalDateTime?,
    val visibility: TournamentVisibility
)

data class UpdateTournamentDTO(
    val tournamentName: String?,
    val description: String?,
    val specification: TournamentSpecification?,
    val scheduledStartTime: LocalDateTime?,
    val visibility: TournamentVisibility?
)
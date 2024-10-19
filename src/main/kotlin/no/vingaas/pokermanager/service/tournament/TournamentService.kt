package no.vingaas.pokermanager.service.tournament

import no.vingaas.pokermanager.dto.tournament.CreateTournamentDTO
import no.vingaas.pokermanager.dto.tournament.UpdateTournamentDTO
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.user.User
import org.springframework.stereotype.Service

@Service
interface TournamentService {
    fun createTournament(createTournamentDTO: CreateTournamentDTO, createdBy: User): Tournament
    fun getTournamentById(id: Long): Tournament?
    fun getAllTournaments(): List<Tournament>
    fun getTournamentsByUser(user: User): List<Tournament>
    fun updateTournament(id: Long, updateTournamentDTO: UpdateTournamentDTO, updatedBy: User): Tournament
    fun deleteTournament(id: Long): Boolean

    fun getAccessibleTournaments(user: User): List<Tournament>
}
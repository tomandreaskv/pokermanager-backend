package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.tournament.TournamentVisibility
import no.vingaas.pokermanager.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TournamentRepository : JpaRepository<Tournament, Long> {
    // Henter turneringer som brukeren enten er deltaker i eller har opprettet
    fun findByParticipantsContainsOrCreatedBy(participant: User, createdBy: User): List<Tournament>

    // Henter turneringer basert på synlighet eller deltakelse/opprettelse av brukeren
    @Query("SELECT t FROM Tournament t WHERE t.visibility = :visibility OR :user MEMBER OF t.participants OR t.createdBy = :user")
    fun findByVisibilityOrParticipantsContainsOrCreatedBy(
        visibility: TournamentVisibility, user: User
    ): List<Tournament>
}
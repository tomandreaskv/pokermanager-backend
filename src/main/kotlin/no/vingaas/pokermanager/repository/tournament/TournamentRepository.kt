package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.Tournament
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentRepository : JpaRepository<Tournament, Long> {
}
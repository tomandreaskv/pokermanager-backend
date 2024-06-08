package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.TournamentRound
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentRoundRepository : JpaRepository<TournamentRound, Long> {
}
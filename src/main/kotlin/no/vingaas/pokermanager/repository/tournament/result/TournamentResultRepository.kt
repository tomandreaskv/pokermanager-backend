package no.vingaas.pokermanager.repository.tournament.result

import no.vingaas.pokermanager.entities.tournament.result.TournamentResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentResultRepository : JpaRepository<TournamentResult, Long> {
}
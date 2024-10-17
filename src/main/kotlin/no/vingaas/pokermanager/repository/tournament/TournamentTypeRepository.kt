package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.TournamentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentTypeRepository : JpaRepository<TournamentType, Long> {
}
package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.TournamentSpecification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TournamentSpecificationRepository : JpaRepository<TournamentSpecification, Long> {
}
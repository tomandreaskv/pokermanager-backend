package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.SeriesTournament
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeriesTournamentRepository : JpaRepository<SeriesTournament, Long> {
}
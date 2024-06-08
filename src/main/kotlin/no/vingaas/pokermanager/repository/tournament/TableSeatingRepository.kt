package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.TableSeating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TableSeatingRepository : JpaRepository<TableSeating, Long> {
}
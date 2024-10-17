package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.Series
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeriesRepository : JpaRepository<Series, Long> {
}
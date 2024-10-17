package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.PrizeDistribution
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrizeDistributionRepository : JpaRepository<PrizeDistribution, Long> {
}
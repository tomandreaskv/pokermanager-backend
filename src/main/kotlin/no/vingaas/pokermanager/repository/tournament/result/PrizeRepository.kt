package no.vingaas.pokermanager.repository.tournament.result

import no.vingaas.pokermanager.entities.tournament.result.Prize
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrizeRepository : JpaRepository<Prize, Long> {
}
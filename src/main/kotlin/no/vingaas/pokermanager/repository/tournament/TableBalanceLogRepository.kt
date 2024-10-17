package no.vingaas.pokermanager.repository.tournament

import no.vingaas.pokermanager.entities.tournament.TableBalanceLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TableBalanceLogRepository : JpaRepository<TableBalanceLog, Long> {
}
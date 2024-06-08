package no.vingaas.pokermanager.repository.security

import no.vingaas.pokermanager.entities.security.UserActivityLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserActivityLogRepository : JpaRepository<UserActivityLog, Long> {
}
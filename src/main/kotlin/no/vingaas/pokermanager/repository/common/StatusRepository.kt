package no.vingaas.pokermanager.repository.common

import no.vingaas.pokermanager.entities.common.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StatusRepository : JpaRepository<Status, Long> {
}
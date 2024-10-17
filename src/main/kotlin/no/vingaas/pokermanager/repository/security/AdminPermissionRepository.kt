package no.vingaas.pokermanager.repository.security

import no.vingaas.pokermanager.entities.security.AdminPermission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminPermissionRepository : JpaRepository<AdminPermission, Long> {
}
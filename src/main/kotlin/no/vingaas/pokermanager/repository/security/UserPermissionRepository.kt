package no.vingaas.pokermanager.repository.security

import no.vingaas.pokermanager.entities.security.UserPermission
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserPermissionRepository : JpaRepository<UserPermission, Long>{
}
package no.vingaas.pokermanager.repository.user

import no.vingaas.pokermanager.entities.user.UserDetail
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDetailRepository : JpaRepository<UserDetail, Long> {
}

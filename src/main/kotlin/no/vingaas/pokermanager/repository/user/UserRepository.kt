package no.vingaas.pokermanager.repository.user

import no.vingaas.pokermanager.entities.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
}
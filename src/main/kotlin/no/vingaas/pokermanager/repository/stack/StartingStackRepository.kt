package no.vingaas.pokermanager.repository.stack

import no.vingaas.pokermanager.entities.stack.StartingStack
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StartingStackRepository : JpaRepository<StartingStack, Long> {
}
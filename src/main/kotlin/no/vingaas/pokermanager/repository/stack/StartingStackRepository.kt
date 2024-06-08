package no.vingaas.pokermanager.repository.stack

import no.vingaas.pokermanager.entities.stack.StartingStack
import org.springframework.data.jpa.repository.JpaRepository

interface StartingStackRepository : JpaRepository<StartingStack, Long> {
}
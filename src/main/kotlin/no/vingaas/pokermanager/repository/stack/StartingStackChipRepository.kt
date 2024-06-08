package no.vingaas.pokermanager.repository.stack

import no.vingaas.pokermanager.entities.stack.StartingStackChip
import org.springframework.data.jpa.repository.JpaRepository

interface StartingStackChipRepository : JpaRepository<StartingStackChip, Long> {
}
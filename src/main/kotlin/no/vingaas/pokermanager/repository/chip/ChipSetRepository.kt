package no.vingaas.pokermanager.repository.chip

import no.vingaas.pokermanager.entities.chip.ChipSet
import org.springframework.data.jpa.repository.JpaRepository

interface ChipSetRepository : JpaRepository<ChipSet, Long> {
}
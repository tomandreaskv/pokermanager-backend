package no.vingaas.pokermanager.repository.chip

import no.vingaas.pokermanager.entities.chip.ChipValue
import org.springframework.data.jpa.repository.JpaRepository

interface ChipValueRepository : JpaRepository<ChipValue, Long> {
}
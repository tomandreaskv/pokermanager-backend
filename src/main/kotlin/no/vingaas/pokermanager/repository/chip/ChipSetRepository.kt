package no.vingaas.pokermanager.repository.chip

import no.vingaas.pokermanager.entities.chip.ChipSet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChipSetRepository : JpaRepository<ChipSet, Long> {
}
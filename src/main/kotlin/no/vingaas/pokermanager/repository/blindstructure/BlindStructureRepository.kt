package no.vingaas.pokermanager.repository.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindStructure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BlindStructureRepository : JpaRepository<BlindStructure, Long> {
}
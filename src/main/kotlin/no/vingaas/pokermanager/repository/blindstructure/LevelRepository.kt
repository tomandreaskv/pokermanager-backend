package no.vingaas.pokermanager.repository.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindLevel
import no.vingaas.pokermanager.entities.blindstructure.BreakLevel
import no.vingaas.pokermanager.entities.blindstructure.Level
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LevelRepository : JpaRepository<Level, Long> {
    @Query("SELECT l FROM Level l WHERE TYPE(l) = BlindLevel AND l.blindStructure.id = :blindStructureId")
    fun findBlindLevelsByBlindStructureId(blindStructureId: Long): List<BlindLevel>

    @Query("SELECT l FROM Level l WHERE TYPE(l) = BreakLevel AND l.blindStructure.id = :blindStructureId")
    fun findBreakLevelsByBlindStructureId(blindStructureId: Long): List<BreakLevel>
}
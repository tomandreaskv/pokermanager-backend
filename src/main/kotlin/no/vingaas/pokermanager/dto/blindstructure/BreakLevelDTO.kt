package no.vingaas.pokermanager.dto.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindStructure

data class BreakLevelDTO(
    override var id: Long,
    override val blindStructure: BlindStructure,
    override val levelOrder: Int,
    override val duration: Int,
    val colorUp: Boolean
) : LevelDTO(id,blindStructure, levelOrder, duration)
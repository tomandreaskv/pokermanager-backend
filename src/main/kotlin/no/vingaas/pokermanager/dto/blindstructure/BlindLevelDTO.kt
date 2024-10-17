package no.vingaas.pokermanager.dto.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindStructure

data class BlindLevelDTO(
    override var id: Long,
    override val blindStructure: BlindStructure,
    override val levelOrder: Int,
    override val duration: Int,
    val smallBlind: Int,
    val bigBlind: Int,
    val ante: Int?
) : LevelDTO(id, blindStructure, levelOrder, duration)

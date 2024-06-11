package no.vingaas.pokermanager.dto.blindstructure

data class BlindLevelDTO(
    override var id: Long,
    override val blindStructureId: Long,
    override val levelOrder: Int,
    override val duration: Int,
    val smallBlind: Int,
    val bigBlind: Int,
    val ante: Int?
) : LevelDTO(id, blindStructureId, levelOrder, duration)

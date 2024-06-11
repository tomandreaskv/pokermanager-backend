package no.vingaas.pokermanager.dto.blindstructure

data class BreakLevelDTO(
    override var id: Long,
    override val blindStructureId: Long,
    override val levelOrder: Int,
    override val duration: Int,
    val colorUp: Boolean
) : LevelDTO(id,blindStructureId, levelOrder, duration)
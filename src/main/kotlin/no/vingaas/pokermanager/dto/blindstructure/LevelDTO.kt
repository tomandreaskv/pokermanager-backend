package no.vingaas.pokermanager.dto.blindstructure

open class LevelDTO(
    open val id: Long,
    open val blindStructureId: Long,
    open val levelOrder: Int,
    open val duration: Int
)
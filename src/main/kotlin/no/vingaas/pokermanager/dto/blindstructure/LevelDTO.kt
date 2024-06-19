package no.vingaas.pokermanager.dto.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindStructure

open class LevelDTO(
    open var id: Long,
    open val blindStructure: BlindStructure,
    open val levelOrder: Int,
    open val duration: Int
)
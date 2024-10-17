package no.vingaas.pokermanager.dto.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindStructure

data class CreateLevelDTO(
    val blindStructureId: BlindStructure,
    val type: String,
    val level: Int,
    val smallBlind: Int? = null,
    val bigBlind: Int? = null,
    val ante: Int? = null,
    val duration: Int,
    val colorUp: Boolean? = null
)
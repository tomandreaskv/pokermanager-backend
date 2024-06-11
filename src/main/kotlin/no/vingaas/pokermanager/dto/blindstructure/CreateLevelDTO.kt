package no.vingaas.pokermanager.dto.blindstructure

data class CreateLevelDTO(
    val blindStructureId: Long,
    val type: String,
    val level: Int,
    val smallBlind: Int? = null,
    val bigBlind: Int? = null,
    val ante: Int? = null,
    val duration: Int,
    val colorUp: Boolean? = null
)
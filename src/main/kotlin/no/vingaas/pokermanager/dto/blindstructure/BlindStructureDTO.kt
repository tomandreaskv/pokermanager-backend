package no.vingaas.pokermanager.dto.blindstructure

data class BlindStructureDTO(
    val id: Long,
    val name: String,
    val blindLevels: List<BlindLevelDTO>
)

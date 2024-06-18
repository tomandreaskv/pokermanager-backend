package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.dto.blindstructure.BlindLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.BlindStructureDTO
import no.vingaas.pokermanager.dto.blindstructure.BreakLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.LevelDTO
import no.vingaas.pokermanager.entities.blindstructure.BlindLevel
import no.vingaas.pokermanager.entities.blindstructure.BlindStructure
import no.vingaas.pokermanager.entities.blindstructure.BreakLevel
import no.vingaas.pokermanager.entities.blindstructure.Level
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

object BlindStructureMapper {
    fun toDTO(blindStructure: BlindStructure): BlindStructureDTO {
        return BlindStructureDTO(
            id = blindStructure.id,
            name = blindStructure.name,
            blindLevels = blindStructure.levels.map { toSpecificLevelDTO(it) }
        )
    }

    private fun toSpecificLevelDTO(level: Level): LevelDTO {
        return when (level) {
            is BlindLevel -> BlindLevelDTO(
                level.id,
                level.blindStructureId,
                level.levelOrder,
                level.duration,
                level.smallBlind,
                level.bigBlind,
                level.ante
            )
            is BreakLevel -> BreakLevelDTO(
                level.id,
                level.blindStructureId,
                level.levelOrder,
                level.duration,
                level.colorUp
            )
            else -> throw IllegalArgumentException("Unknown level type")
        }
    }

    fun toEntity(blindStructureDTO: BlindStructureDTO, createdBy: User): BlindStructure {
        val now = LocalDateTime.now()
        return BlindStructure(
            id = blindStructureDTO.id,
            name = blindStructureDTO.name,
            levels = blindStructureDTO.blindLevels.map { toSpecificLevelEntity(it) },
            createdBy = createdBy,
            createdAt = now,
            updatedAt = now
        )
    }

    private fun toSpecificLevelEntity(levelDTO: LevelDTO): Level {
        return when (levelDTO) {
            is BlindLevelDTO -> BlindLevel(
                id = levelDTO.id,
                blindStructureId = levelDTO.blindStructureId,
                levelOrder = levelDTO.levelOrder,
                duration = levelDTO.duration,
                smallBlind = levelDTO.smallBlind,
                bigBlind = levelDTO.bigBlind,
                ante = levelDTO.ante
            )
            is BreakLevelDTO -> BreakLevel(
                id = levelDTO.id,
                blindStructureId = levelDTO.blindStructureId,
                levelOrder = levelDTO.levelOrder,
                duration = levelDTO.duration,
                colorUp = levelDTO.colorUp
            )
            else -> throw IllegalArgumentException("Unknown level type")
        }
    }
}
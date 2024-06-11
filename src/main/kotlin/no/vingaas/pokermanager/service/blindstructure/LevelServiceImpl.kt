package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.dto.blindstructure.BlindLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.BreakLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.CreateLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.LevelDTO
import no.vingaas.pokermanager.entities.blindstructure.BlindLevel
import no.vingaas.pokermanager.entities.blindstructure.BreakLevel
import no.vingaas.pokermanager.entities.blindstructure.Level
import no.vingaas.pokermanager.repository.blindstructure.LevelRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LevelServiceImpl(private val levelRepository: LevelRepository) : LevelService {
    private val logger = LoggerFactory.getLogger(LevelServiceImpl::class.java)

    override fun getLevelWithId(id: Long): LevelDTO {
        logger.info("Fetching level with id: $id")
        val level = levelRepository.findById(id).orElseThrow { IllegalArgumentException("No Level with id: $id") }
        return mapToDTO(level)
    }

    override fun getAllLevels(): List<LevelDTO> {
        logger.info("Fetching all levels")
        return levelRepository.findAll().map { mapToDTO(it) }
    }

    override fun createLevel(createLevelDTO: CreateLevelDTO): LevelDTO {
        logger.info("Creating level with type: ${createLevelDTO.type}")
        val level = when (createLevelDTO.type) {
            "blind" -> createBlindLevel(createLevelDTO)
            "break" -> createBreakLevel(createLevelDTO)
            else -> throw IllegalArgumentException("The type: ${createLevelDTO.type} is not a valid level type")
        }
        levelRepository.save(level)
        return mapToDTO(level)
    }

    override fun updateLevel(levelDTO: LevelDTO): LevelDTO {
        logger.info("Updating level with id: ${levelDTO.id}")
        val existingLevel = levelRepository.findById(levelDTO.id).orElseThrow { IllegalArgumentException("No Level with id: ${levelDTO.id}") }
        val updatedLevel = when (existingLevel) {
            is BlindLevel -> updateBlindLevel(levelDTO as BlindLevelDTO, existingLevel)
            is BreakLevel -> updateBreakLevel(levelDTO as BreakLevelDTO, existingLevel)
            else -> throw IllegalArgumentException("The level with id: ${levelDTO.id} is not a BlindLevel or BreakLevel")
        }
        levelRepository.save(updatedLevel)
        return mapToDTO(updatedLevel)
    }

    override fun deleteLevel(id: Long) {
        logger.info("Deleting level with id: $id")
        if (!levelRepository.existsById(id)) {
            throw IllegalArgumentException("No Level with id: $id")
        }
        levelRepository.deleteById(id)
    }

    private fun mapToDTO(level: Level): LevelDTO {
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

    private fun createBlindLevel(dto: CreateLevelDTO): BlindLevel {
        return BlindLevel(
            blindStructureId = dto.blindStructureId,
            levelOrder = dto.level,
            duration = dto.duration,
            smallBlind = dto.smallBlind!!,
            bigBlind = dto.bigBlind!!,
            ante = dto.ante
        )
    }

    private fun createBreakLevel(dto: CreateLevelDTO): BreakLevel {
        return BreakLevel(
            blindStructureId = dto.blindStructureId,
            levelOrder = dto.level,
            duration = dto.duration,
            colorUp = dto.colorUp!!
        )
    }

    private fun updateBlindLevel(dto: BlindLevelDTO, existingLevel: BlindLevel): BlindLevel {
        return existingLevel.copy(
            levelOrder = dto.levelOrder,
            duration = dto.duration,
            smallBlind = dto.smallBlind,
            bigBlind = dto.bigBlind,
            ante = dto.ante
        )
    }

    private fun updateBreakLevel(dto: BreakLevelDTO, existingLevel: BreakLevel): BreakLevel {
        return existingLevel.copy(
            levelOrder = dto.levelOrder,
            duration = dto.duration,
            colorUp = dto.colorUp
        )
    }
}
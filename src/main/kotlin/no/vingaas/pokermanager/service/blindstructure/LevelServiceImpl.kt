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

    override fun getLevelWithId(id: Long): Level {
        logger.info("Fetching level with id: $id")
       return levelRepository.findById(id).orElseThrow { IllegalArgumentException("No Level with id: $id") }
    }

    override fun getAllLevels(): List<Level> {
        logger.info("Fetching all levels")
        return levelRepository.findAll()
    }

    override fun createLevel(level: Level): Level {
        logger.info("Creating level with type: ${level.javaClass.simpleName}")
        return levelRepository.save(level)
    }

    override fun updateLevel(level: Level): Level {
        logger.info("Updating level with id: ${level.id}")
        val existingLevel = levelRepository.findById(level.id).orElseThrow { IllegalArgumentException("No Level with id: ${level.id}") }
        return levelRepository.save(existingLevel)
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
                level.blindStructure,
                level.levelOrder,
                level.duration,
                level.smallBlind,
                level.bigBlind,
                level.ante
            )
            is BreakLevel -> BreakLevelDTO(
                level.id,
                level.blindStructure,
                level.levelOrder,
                level.duration,
                level.colorUp
            )
            else -> throw IllegalArgumentException("Unknown level type")
        }
    }

    private fun createBlindLevel(dto: CreateLevelDTO): BlindLevel {
        return BlindLevel(
            blindStructure = dto.blindStructureId,
            levelOrder = dto.level,
            duration = dto.duration,
            smallBlind = dto.smallBlind!!,
            bigBlind = dto.bigBlind!!,
            ante = dto.ante
        )
    }

    private fun createBreakLevel(dto: CreateLevelDTO): BreakLevel {
        return BreakLevel(
            blindStructure = dto.blindStructureId,
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
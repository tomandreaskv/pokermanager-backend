package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.dto.blindstructure.BlindLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.BreakLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.CreateLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.LevelDTO
import no.vingaas.pokermanager.entities.blindstructure.BlindLevel
import no.vingaas.pokermanager.entities.blindstructure.BreakLevel
import no.vingaas.pokermanager.repository.blindstructure.LevelRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LevelServiceImpl(private val blindLevelRepository: LevelRepository) : LevelService {
    private val logger =  LoggerFactory.getLogger(LevelServiceImpl::class.java)
    override fun getLevelWithId(id: Long): LevelDTO {
        logger.info("Fetching level with id: $id")
        return when (val level = blindLevelRepository.findById(id).get()) {
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
            else -> throw IllegalArgumentException("The level with id: $id is not a BlindLevel or BreakLevel")
        }
    }

    override fun getAllLevels(): List<LevelDTO> {
        logger.info("Fetching all levels")
        return blindLevelRepository.findAll().map {
            when (it) {
                is BlindLevel -> BlindLevelDTO(
                    it.id,
                    it.blindStructureId,
                    it.levelOrder,
                    it.duration,
                    it.smallBlind,
                    it.bigBlind,
                    it.ante
                )
                is BreakLevel -> BreakLevelDTO(
                    it.id,
                    it.blindStructureId,
                    it.levelOrder,
                    it.duration,
                    it.colorUp
                )
                else -> throw IllegalArgumentException("The level with id: ${it.id} is not a BlindLevel or BreakLevel")
            }
        }
    }

    override fun createLevel(createLevelDTO: CreateLevelDTO): LevelDTO {
        logger.info("Creating level with type: ${createLevelDTO.type}")
        return when (createLevelDTO.type) {
            "blind" -> {
                val blindLevel = BlindLevel(
                    blindStructureId =  createLevelDTO.blindStructureId,
                    levelOrder = createLevelDTO.level,
                    smallBlind = createLevelDTO.smallBlind!!,
                    bigBlind = createLevelDTO.bigBlind!!,
                    ante = createLevelDTO.ante,
                    duration = createLevelDTO.duration
                )
                blindLevelRepository.save(blindLevel)
                BlindLevelDTO(
                    blindLevel.id,
                    blindLevel.blindStructureId,
                    blindLevel.levelOrder,
                    blindLevel.duration,
                    blindLevel.smallBlind,
                    blindLevel.bigBlind,
                    blindLevel.ante
                )
            }
            "break" -> {
                val breakLevel = BreakLevel(
                    blindStructureId =  createLevelDTO.blindStructureId,
                    levelOrder = createLevelDTO.level,
                    duration = createLevelDTO.duration,
                    colorUp = createLevelDTO.colorUp!!
                )
                blindLevelRepository.save(breakLevel)
                BreakLevelDTO(
                    breakLevel.id,
                    breakLevel.blindStructureId,
                    breakLevel.levelOrder,
                    breakLevel.duration,
                    breakLevel.colorUp
                )
            }
            else -> throw IllegalArgumentException("The type: ${createLevelDTO.type} is not a valid level type")
        }
    }

    override fun updateLevel(levelDTO: LevelDTO): LevelDTO {
        logger.info("Updating level with id: ${levelDTO.id}")
        return when (val level = blindLevelRepository.findById(levelDTO.id).get()) {
            is BlindLevel -> {
                val blindLevel = BlindLevel(
                    id = levelDTO.id,
                    blindStructureId = levelDTO.blindStructureId,
                    levelOrder = levelDTO.levelOrder,
                    duration = levelDTO.duration,
                    smallBlind = (levelDTO as BlindLevelDTO).smallBlind,
                    bigBlind = (levelDTO as BlindLevelDTO).bigBlind,
                    ante = (levelDTO as BlindLevelDTO).ante
                )
                blindLevelRepository.save(blindLevel)
                BlindLevelDTO(
                    blindLevel.id,
                    blindLevel.blindStructureId,
                    blindLevel.levelOrder,
                    blindLevel.duration,
                    blindLevel.smallBlind,
                    blindLevel.bigBlind,
                    blindLevel.ante
                )
            }
            is BreakLevel -> {
                val breakLevel = BreakLevel(
                    id = levelDTO.id,
                    blindStructureId = levelDTO.blindStructureId,
                    levelOrder = levelDTO.levelOrder,
                    duration = levelDTO.duration,
                    colorUp = (levelDTO as BreakLevelDTO).colorUp
                )
                blindLevelRepository.save(breakLevel)
                BreakLevelDTO(
                    breakLevel.id,
                    breakLevel.blindStructureId,
                    breakLevel.levelOrder,
                    breakLevel.duration,
                    breakLevel.colorUp
                )
            }
            else -> throw IllegalArgumentException("The level with id: ${levelDTO.id} is not a BlindLevel or BreakLevel")
        }
    }

    override fun deleteLevel(id: Long) {
        logger.info("Deleting level with id: $id")
        blindLevelRepository.deleteById(id)
    }

}
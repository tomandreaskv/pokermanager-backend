package no.vingaas.pokermanager.service.blindstructure

import jakarta.transaction.Transactional
import no.vingaas.pokermanager.dto.blindstructure.BlindStructureDTO
import no.vingaas.pokermanager.entities.user.User
import no.vingaas.pokermanager.repository.blindstructure.BlindStructureRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class BlindStructureServiceImpl(
    private val blindStructureRepository: BlindStructureRepository
) : BlindStructureService {

    private val logger = LoggerFactory.getLogger(BlindStructureServiceImpl::class.java)

    override fun getBlindStructureWithId(id: Long): BlindStructureDTO {
        logger.info("Fetching blind structure with id: $id")
        val blindStructure = blindStructureRepository.findById(id)
            .orElseThrow { IllegalArgumentException("No BlindStructure with id: $id") }
        return BlindStructureMapper.toDTO(blindStructure)
    }

    override fun createBlindStructure(blindStructureDTO: BlindStructureDTO, createdBy: User): BlindStructureDTO {
        logger.info("Creating blind structure with name: ${blindStructureDTO.name}")
        val blindStructure = BlindStructureMapper.toEntity(blindStructureDTO, createdBy)
        blindStructureRepository.save(blindStructure)
        return BlindStructureMapper.toDTO(blindStructure)
    }

    override fun updateBlindStructure(blindStructureDTO: BlindStructureDTO): BlindStructureDTO {
        logger.info("Updating blind structure with id: ${blindStructureDTO.id}")
        val blindStructure = blindStructureRepository.findById(blindStructureDTO.id)
            .orElseThrow { IllegalArgumentException("No BlindStructure with id: ${blindStructureDTO.id}") }
        blindStructure.blindStructuresName = blindStructureDTO.name
        blindStructure.updatedAt = LocalDateTime.now()
        blindStructureRepository.save(blindStructure)
        return BlindStructureMapper.toDTO(blindStructure)
    }

    override fun deleteBlindStructure(id: Long) {
        logger.info("Deleting blind structure with id: $id")
        if (!blindStructureRepository.existsById(id)) {
            throw IllegalArgumentException("No BlindStructure with id: $id")
        }
        blindStructureRepository.deleteById(id)
    }

    override fun getAllBlindStructures(): List<BlindStructureDTO> {
        logger.info("Fetching all blind structures")
        return blindStructureRepository.findAll().map { BlindStructureMapper.toDTO(it) }
    }
}
package no.vingaas.pokermanager.service.blindstructure

import jakarta.transaction.Transactional
import no.vingaas.pokermanager.entities.blindstructure.BlindStructure
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

    override fun getBlindStructureWithId(id: Long): BlindStructure {
        logger.info("Fetching blind structure with id: $id")
        val blindStructure = blindStructureRepository.findById(id)
            .orElseThrow { IllegalArgumentException("No BlindStructure with id: $id") }
        return blindStructure
    }

    override fun createBlindStructure(blindStructure: BlindStructure, createdBy: User): BlindStructure {
        logger.info("Creating blind structure with name: ${blindStructure.name}")

        return blindStructureRepository.save(blindStructure)
    }

    override fun updateBlindStructure(updatedBlindStructure: BlindStructure): BlindStructure {
        logger.info("Updating blind structure with id: ${updatedBlindStructure.id}")
        val blindStructure = blindStructureRepository.findById(updatedBlindStructure.id)
            .orElseThrow { IllegalArgumentException("No BlindStructure with id: ${updatedBlindStructure.id}") }
        blindStructure.name = updatedBlindStructure.name
        blindStructure.updatedAt = LocalDateTime.now()
        return blindStructureRepository.save(blindStructure)
    }

    override fun deleteBlindStructure(id: Long) {
        logger.info("Deleting blind structure with id: $id")
        if (!blindStructureRepository.existsById(id)) {
            throw IllegalArgumentException("No BlindStructure with id: $id")
        }
        blindStructureRepository.deleteById(id)
    }

    override fun getAllBlindStructures(): List<BlindStructure> {
        logger.info("Fetching all blind structures")
        return blindStructureRepository.findAll()
    }
}
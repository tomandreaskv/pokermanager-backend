package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.dto.blindstructure.BlindStructureDTO
import no.vingaas.pokermanager.entities.user.User
import org.springframework.stereotype.Service

@Service
interface BlindStructureService {
    fun getBlindStructureWithId(id: Long): BlindStructureDTO
    fun createBlindStructure(blindStructureDTO: BlindStructureDTO, createdBy: User): BlindStructureDTO
    fun updateBlindStructure(blindStructureDTO: BlindStructureDTO): BlindStructureDTO
    fun deleteBlindStructure(id: Long)
    fun getAllBlindStructures(): List<BlindStructureDTO>

}
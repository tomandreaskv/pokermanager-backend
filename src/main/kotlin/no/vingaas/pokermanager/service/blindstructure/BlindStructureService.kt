package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.BlindStructure
import no.vingaas.pokermanager.entities.user.User
import org.springframework.stereotype.Service

@Service
interface BlindStructureService {
    fun getBlindStructureWithId(id: Long): BlindStructure
    fun createBlindStructure(blindStructure: BlindStructure, createdBy: User): BlindStructure
    fun updateBlindStructure(blindStructure: BlindStructure): BlindStructure
    fun deleteBlindStructure(id: Long)
    fun getAllBlindStructures(): List<BlindStructure>

}
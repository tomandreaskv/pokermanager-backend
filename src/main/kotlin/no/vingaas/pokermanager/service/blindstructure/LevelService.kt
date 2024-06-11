package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.dto.blindstructure.CreateLevelDTO
import no.vingaas.pokermanager.dto.blindstructure.LevelDTO
import org.springframework.stereotype.Service

@Service
interface LevelService {
    fun getLevelWithId(id: Long): LevelDTO

    fun createLevel(createLevelDTO: CreateLevelDTO): LevelDTO

    fun updateLevel(levelDTO: LevelDTO): LevelDTO

    fun deleteLevel(id: Long)

    fun getAllLevels(): List<LevelDTO>
}
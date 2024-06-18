package no.vingaas.pokermanager.service.blindstructure

import no.vingaas.pokermanager.entities.blindstructure.Level
import org.springframework.stereotype.Service

@Service
interface LevelService {
    fun getLevelWithId(id: Long): Level

    fun createLevel(level: Level): Level

    fun updateLevel(level: Level): Level

    fun deleteLevel(id: Long)

    fun getAllLevels(): List<Level>
}
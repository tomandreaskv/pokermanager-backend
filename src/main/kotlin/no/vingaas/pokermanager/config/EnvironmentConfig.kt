package no.vingaas.pokermanager.config

import io.github.cdimascio.dotenv.dotenv
import org.springframework.stereotype.Component

@Component
class EnvironmentConfig {
    init {
        val dotenv = dotenv()
        System.setProperty("DB_USER", dotenv["DB_USER"])
        System.setProperty("DB_PASS", dotenv["DB_PASS"])
        System.setProperty("DB_DRIVER", dotenv["DB_DRIVER"])
        System.setProperty("DB_HOST", dotenv["DB_HOST"])
        System.setProperty("DB_PORT", dotenv["DB_PORT"])
        System.setProperty("DB_NAME", dotenv["DB_NAME"])
        System.setProperty("DB_SCHEMA", dotenv["DB_SCHEMA"])
        System.setProperty("FLYWAY_DB_SCHEMA", dotenv["DB_SCHEMA"])
        System.setProperty("FLYWAY_LOCATION", dotenv["FLYWAY_LOCATION"])
    }
}
package no.vingaas.pokermanager.config

import jakarta.annotation.PostConstruct
import org.flywaydb.core.Flyway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.sql.DriverManager
import java.util.concurrent.TimeUnit

@Component
class FlywayInitializer(private val environment: Environment) {
    private val logger: Logger = LoggerFactory.getLogger(FlywayInitializer::class.java)
    @PostConstruct
    fun migrateDatabase() {
        logger.info("Starting database migration...")
        val dbUrl = environment.getRequiredProperty("spring.datasource.url")
        val dbUser = environment.getRequiredProperty("spring.datasource.username")
        val dbPass = environment.getRequiredProperty("spring.datasource.password")
        val flywayDbSchema = environment.getRequiredProperty("FLYWAY_DB_SCHEMA")
        val flywayLocation = environment.getRequiredProperty("FLYWAY_LOCATION")

        val flyway = Flyway.configure()
            .dataSource(dbUrl, dbUser, dbPass)
            .locations(flywayLocation)
            .schemas(flywayDbSchema)
            .load()

        flyway.migrate()
        logger.info("Database migration completed.")
    }

    private fun waitForDatabaseToStart(dbUrl: String, dbUser: String, dbPass: String) {
        logger.info("Waiting for database to start...")
        var attempts = 0
        while (attempts < 30) {
            try {
                DriverManager.getConnection(dbUrl, dbUser, dbPass).use { }
                logger.info("Database is up and running!")
                return
            } catch (e: Exception) {
                attempts++
                if (attempts > 10) {
                    logger.warn("Database start attempt exceeded 10 times...")
                }
                logger.info("Waiting for database... attempt: $attempts")
                TimeUnit.SECONDS.sleep(5)
            }
        }
        logger.error("Database did not start after 30 attempts")
        throw RuntimeException("Database did not start after 30 attempts")
    }
}
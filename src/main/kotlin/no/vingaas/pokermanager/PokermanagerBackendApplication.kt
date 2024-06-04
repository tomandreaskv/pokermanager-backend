package no.vingaas.pokermanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PokermanagerBackendApplication

fun main(args: Array<String>) {
	runApplication<PokermanagerBackendApplication>(*args)
}

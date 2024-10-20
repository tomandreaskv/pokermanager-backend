package no.vingaas.pokermanager.repository.common

import no.vingaas.pokermanager.entities.common.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StatusRepository : JpaRepository<Status, Long> {

    @Query("select s from Status s where upper(s.name) = upper(?1)")
    fun findByName(name: String): Optional<Status>

}
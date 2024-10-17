package no.vingaas.pokermanager.repository.equipment

import no.vingaas.pokermanager.entities.equipment.Equipment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipmentRepository : JpaRepository<Equipment, Long> {
}
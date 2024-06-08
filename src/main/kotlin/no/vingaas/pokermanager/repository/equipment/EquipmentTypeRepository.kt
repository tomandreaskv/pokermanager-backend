package no.vingaas.pokermanager.repository.equipment

import no.vingaas.pokermanager.entities.equipment.EquipmentType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EquipmentTypeRepository : JpaRepository<EquipmentType, Long> {
}
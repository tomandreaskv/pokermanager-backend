package no.vingaas.pokermanager.entities.chip

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.equipment.Equipment
import no.vingaas.pokermanager.entities.equipment.EquipmentType
import java.time.LocalDateTime


@Entity
@Table(name = "chip_sets", schema = "pokerman")
data class ChipSet(
    @OneToMany(mappedBy = "chipSet")
    val chipValues: List<ChipValue>,
    val tournamentChipSet: Boolean,

    override val id: Long = 0,
    override val equipmentName: String,
    override val description: String?,
    @ManyToOne
    override val equipmentType: EquipmentType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : Equipment(id, equipmentName, description, equipmentType, createdAt, updatedAt)
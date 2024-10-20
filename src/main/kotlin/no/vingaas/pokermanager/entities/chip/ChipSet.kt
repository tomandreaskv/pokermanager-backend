package no.vingaas.pokermanager.entities.chip

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.equipment.Equipment
import no.vingaas.pokermanager.entities.equipment.EquipmentType
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime


@Entity
@Table(name = "chip_sets", schema = "pokerman")
data class ChipSet(
    @OneToMany(mappedBy = "chipSet", cascade = [CascadeType.ALL], orphanRemoval = true)
    val chipValues: List<ChipValue>,
    val tournamentChipSet: Boolean,

    override val id: Long = 0,
    override val equipmentName: String,
    override val description: String?,
    @ManyToOne
    override val equipmentType: EquipmentType,
    override val createdAt: LocalDateTime,
    @ManyToOne
    override val createdBy: User?,
    override val updatedAt: LocalDateTime
) : Equipment(id, equipmentName, description, equipmentType, createdBy, createdAt, updatedAt)
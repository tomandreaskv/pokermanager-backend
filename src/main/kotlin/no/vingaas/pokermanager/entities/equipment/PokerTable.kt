package no.vingaas.pokermanager.entities.equipment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "poker_tables", schema = "pokerman")
data class PokerTable(
    @Column(nullable = false)
    val seats: Int,

    override val id: Long = 0,
    override val equipmentName: String,
    override val description: String?,
    @ManyToOne
    override val equipmentType: EquipmentType,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : Equipment(id, equipmentName,description, equipmentType, createdAt, updatedAt)
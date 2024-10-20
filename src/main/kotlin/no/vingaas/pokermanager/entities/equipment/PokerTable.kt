package no.vingaas.pokermanager.entities.equipment

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import no.vingaas.pokermanager.entities.user.User
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
    @ManyToOne
    override val createdBy: User?,
    override val createdAt: LocalDateTime,
    override val updatedAt: LocalDateTime
) : Equipment(id, equipmentName,description, equipmentType, createdBy, createdAt, updatedAt)
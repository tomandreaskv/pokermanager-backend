package no.vingaas.pokermanager.entities.equipment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "equipments")
data class Equipment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column( name = "equipment_name", nullable = false)
    val equipmentName: String,

    @ManyToOne
    @JoinColumn(name = "equipment_type_id", nullable = false)
    val equipmentType: EquipmentType,

    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @Column(nullable = false)
    val updatedAt: LocalDateTime
)
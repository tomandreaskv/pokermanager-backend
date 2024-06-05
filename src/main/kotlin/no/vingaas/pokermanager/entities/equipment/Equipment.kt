package no.vingaas.pokermanager.entities.equipment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "equipments", schema = "pokerman")
data class Equipment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column( name = "equipment_name", nullable = false)
    val equipmentName: String,

    @ManyToOne
    @JoinColumn(name = "equipment_type_id", nullable = false)
    val equipmentType: EquipmentType,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime
)
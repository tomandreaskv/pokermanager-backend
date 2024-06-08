package no.vingaas.pokermanager.entities.equipment

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "equipments", schema = "pokerman")
@Inheritance(strategy = InheritanceType.JOINED)
open class Equipment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0,

    @Column(name = "equipment_name", nullable = false)
    open val equipmentName: String,

    open val description: String?,

    @ManyToOne
    @JoinColumn(name = "equipment_type_id", nullable = false)
    open val equipmentType: EquipmentType,

    @Column(name = "created_at", nullable = false)
    open val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    open val updatedAt: LocalDateTime
)
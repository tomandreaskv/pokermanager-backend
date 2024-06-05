package no.vingaas.pokermanager.entities.equipment

import jakarta.persistence.*

@Entity
@Table(name = "equipment_types")
data class EquipmentType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name ="type_name", nullable = false, unique = true)
    val typeName: String,

    val description: String?
)
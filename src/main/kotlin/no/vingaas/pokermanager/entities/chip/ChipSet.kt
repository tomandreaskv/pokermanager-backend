package no.vingaas.pokermanager.entities.chip

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.equipment.Equipment


@Entity
@Table(name = "chip_sets", schema = "pokerman")
data class ChipSet(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "chip_set_name", nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    val equipment: Equipment
)
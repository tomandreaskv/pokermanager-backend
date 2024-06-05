package no.vingaas.pokermanager.entities.chip

import jakarta.persistence.*

@Entity
@Table(name = "chip_values", schema = "pokerman")
data class ChipValue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "chip_set_id", nullable = false)
    val chipSet: ChipSet,

    @Column( name = "chip_value", nullable = false)
    val chipValue: Int,

    @Column(nullable = false)
    val quantity: Int
)
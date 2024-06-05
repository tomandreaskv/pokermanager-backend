package no.vingaas.pokermanager.entities.stack

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.chip.ChipValue

@Entity
@Table(name = "starting_stack_chips")
data class StartingStackChip(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "starting_stack_id", nullable = false)
    val startingStack: StartingStack,

    @ManyToOne
    @JoinColumn(name = "chip_value_id", nullable = false)
    val chipValue: ChipValue,

    @Column(nullable = false)
    val quantity: Int
)
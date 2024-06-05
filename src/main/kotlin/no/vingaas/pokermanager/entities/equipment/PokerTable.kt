package no.vingaas.pokermanager.entities.equipment

import jakarta.persistence.*

@Entity
@Table(name = "poker_tables")
data class PokerTable(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    val equipment: Equipment,

    @Column(nullable = false)
    val seats: Int
)
package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*

@Entity
@Table(name = "prize_distributions", schema = "pokerman")
data class PrizeDistribution(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "specification_id", nullable = false)
    val specification: TournamentSpecification,

    @Column(nullable = false)
    val position: Int,

    @Column(nullable = false)
    val percentage: Double
)
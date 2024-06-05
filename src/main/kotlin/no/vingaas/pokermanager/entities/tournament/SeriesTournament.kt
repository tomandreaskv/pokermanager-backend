package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*

@Entity
@Table(name = "series_tournaments")
data class SeriesTournament(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "series_id")
    val series: Series,

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    val tournament: Tournament
)
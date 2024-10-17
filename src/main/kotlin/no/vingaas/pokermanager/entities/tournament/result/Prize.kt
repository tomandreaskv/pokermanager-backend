package no.vingaas.pokermanager.entities.tournament.result

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "prizes", schema = "pokerman")
data class Prize(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_result_id", nullable = false)
    val tournamentResult: TournamentResult,

    @Column(nullable = false)
    val type: String,

    @Column(nullable = false)
    val amount: Double,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime
)
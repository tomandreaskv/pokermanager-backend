package no.vingaas.pokermanager.entities.tournament.result

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "tournament_results", schema = "pokerman")
data class TournamentResult(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val position: Int,

    @Column(nullable = false)
    val points: Int,
    @Column(name = "bounties_won")
    val bountiesWon: Int?,
    @Column(name = "won_prize")
    val wonPrize: Boolean?,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime
)
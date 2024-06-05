package no.vingaas.pokermanager.security

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.user.User


@Entity
@Table(name = "user_permissions")
data class UserPermission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,
    @Column(name = "can_start_pause_stop")
    val canStartPauseStop: Boolean?,
    @Column(name = "can_edit_players")
    val canEditPrizeDistribution: Boolean?,
    @Column(name = "can_edit_prize_distribution")
    val canEditSettings: Boolean?
)
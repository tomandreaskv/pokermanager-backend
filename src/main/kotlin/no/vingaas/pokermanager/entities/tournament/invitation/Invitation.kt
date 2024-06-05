package no.vingaas.pokermanager.entities.tournament.invitation

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.common.Status
import no.vingaas.pokermanager.entities.tournament.Tournament
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "invitations")
data class Invitation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User?,

    @ManyToOne
    @JoinColumn(name = "from_user_id", nullable = false)
    val fromUser: User,

    val email: String?,

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    val status: Status,

    @Column(name = "sent_at", nullable = false)
    val sentAt: LocalDateTime
)
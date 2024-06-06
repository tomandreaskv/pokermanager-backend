package no.vingaas.pokermanager.entities.security

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "user_activity_logs", schema = "pokerman")
data class UserActivityLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "activity_action", nullable = false)
    val activityAction: String,

    @Column(name = "activity_timestamp", nullable = false)
    val activityTimestamp: LocalDateTime,

    val details: String?
)
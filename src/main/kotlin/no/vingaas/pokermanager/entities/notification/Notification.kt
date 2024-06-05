package no.vingaas.pokermanager.entities.notification

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "notifications")
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val message: String,

    @Column(nullable = false)
    val isRead: Boolean,

    @Column(nullable = false)
    val sentAt: LocalDateTime
)
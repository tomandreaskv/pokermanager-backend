package no.vingaas.pokermanager.entities.user

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users", schema = "pokerman")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val username: String,
    val password: String,
    val email: String,

    @Column(name = "is_admin")
    val isAdmin: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime
)

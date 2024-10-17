package no.vingaas.pokermanager.entities.user

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_credential", schema = "pokerman")
data class UserCredential(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id")
    val userId: Long,

    val password: String,
    @Column(name = "is_temporal")
    val isTemporal: Boolean = false,
    @Column(name = "is_active")
    val isActive: Boolean = true,
    @Column(name = "valid_from_date_time")
    val validToDateTime: LocalDateTime? = null,
    @Column(name = "created_at")
    val createdAt : LocalDateTime = LocalDateTime.now(),
)
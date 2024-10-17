package no.vingaas.pokermanager.entities.auth

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "refresh_tokens", schema = "pokerman")
data class RefreshToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @Column(nullable = false)
    val token: String,

    @Column(nullable = false)
    val expiration: LocalDateTime,  // Endret til LocalDateTime for å inkludere tid

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),  // Når tokenet ble opprettet

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TokenStatus = TokenStatus.VALID,

    @Column(name = "ip_address")
    val ipAddress: String? = null,  // Valgfritt: IP-adresse til enheten som opprettet tokenet

    @Column(name = "device_info")
    val deviceInfo: String? = null  // Valgfritt: Informasjon om enheten
)
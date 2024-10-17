package no.vingaas.pokermanager.entities.auth

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "access_tokens", schema = "pokerman")
data class AccessToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "refresh_token_id")
    val refreshToken: RefreshToken,  // Fremmednøkkel til refresh token

    @Column(nullable = false)
    val jti: String,  // Unik identifikator for access token

    @Column(nullable = false)
    val expiration: LocalDateTime,  // Utløpsdato for access tokenet

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),  // Når access tokenet ble opprettet

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: TokenStatus = TokenStatus.VALID,

    @Column(name = "ip_address")
    val ipAddress: String? = null,  // Valgfritt: IP-adresse til enheten som brukte tokenet

    @Column(name = "device_info")
    val deviceInfo: String? = null  // Valgfritt: Enhetsinformasjon
)
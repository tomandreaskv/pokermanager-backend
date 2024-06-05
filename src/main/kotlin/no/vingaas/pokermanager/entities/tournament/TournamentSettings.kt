package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tournament_settings")
data class TournamentSettings(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val bounty: Long? = null,

    @Column(name = "max_rebuys")
    val maxRebuys: Int? = null,

    @Column(name = "rebuy_end_time")
    val rebuyEndTime: LocalDateTime? = null
)
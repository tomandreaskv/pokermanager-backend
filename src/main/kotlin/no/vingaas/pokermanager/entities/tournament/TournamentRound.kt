package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.blindstructure.Level
import no.vingaas.pokermanager.entities.common.Status
import java.time.LocalDateTime

@Entity
@Table(name = "tournament_rounds", schema = "pokerman")
data class TournamentRound(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,

    @ManyToOne
    @JoinColumn(name = "level_id", nullable = false)
    val level: Level,

    @ManyToOne
    @JoinColumn(name = "round_status_id", nullable = false)
    val roundStatus: Status,
    @Column(name = "scheduled_start_time")
    val scheduledStartTime: LocalDateTime?,
    @Column(name = "actual_start_time")
    val actualStartTime: LocalDateTime?,
    @Column(name = "paused_time")
    val pausedTime: LocalDateTime?,
    @Column(name = "end_time")
    val endTime: LocalDateTime?,
    @Column(name = "remaining_time")
    val remainingTime: Int?
)
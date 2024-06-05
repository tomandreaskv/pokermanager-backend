package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.blindstructure.BlindStructure
import no.vingaas.pokermanager.entities.stack.StartingStack
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "tournaments")
data class Tournament(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "tournament_name", nullable = false)
    val tournamentName: String,

    val description: String?,

    @ManyToOne
    @JoinColumn(name = "tournament_type_id", nullable = false)
    val tournamentType: TournamentType,
    @Column(name = "buy_in", nullable = false)
    val buyIn: Double?,

    @Column(name = "free_to_play", nullable = false)
    val freeToPlay: Boolean,

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    val status: Status,
    @Column(name = "scheduled_start_time")
    val scheduledStartTime: LocalDateTime?,
    @Column(name = "actual_start_time")
    val actualStartTime: LocalDateTime?,
    @Column(name = "end_time")
    val endTime: LocalDateTime?,
    @Column(name = "current_level")
    val currentLevel: Int?,
    @Column(name = "remaining_level_time")
    val remainingLevelTime: Int?,

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    val createdBy: User,

    @ManyToOne
    @JoinColumn(name = "blind_structure_id")
    val blindStructure: BlindStructure?,

    @ManyToOne
    @JoinColumn(name = "starting_stack_id")
    val startingStack: StartingStack?,

    @ManyToOne
    @JoinColumn(name = "settings_id")
    val settings: TournamentSettings?,

    @ManyToOne
    @JoinColumn(name = "series_id")
    val series: Series?,

    @Column( name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime
)
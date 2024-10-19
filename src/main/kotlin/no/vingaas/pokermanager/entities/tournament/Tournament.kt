package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.common.Status
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "tournaments", schema = "pokerman")
data class Tournament(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "tournament_name", nullable = false)
    val tournamentName: String,

    val description: String?,

    @ManyToOne
    @JoinColumn(name = "specification_id", nullable = false)
    val specification: TournamentSpecification,

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    val status: Status,

    @Column(name = "scheduled_start_time")
    val scheduledStartTime: LocalDateTime?  = null,

    @Column(name = "actual_start_time")
    val actualStartTime: LocalDateTime?  = null,

    @Column(name = "end_time")
    val endTime: LocalDateTime?  = null,

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    val createdBy: User,

    @ManyToOne
    @JoinColumn(name = "series_id")
    val series: Series? = null,

    @Column( name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    val visibility: TournamentVisibility = TournamentVisibility.PUBLIC,

    @ManyToMany
    @JoinTable(
        name = "user_tournaments",
        joinColumns = [JoinColumn(name = "tournament_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val participants: List<User> = mutableListOf(),

    @OneToMany(mappedBy = "tournament")
    val rounds: List<TournamentRound> = mutableListOf(),

    @OneToOne
    @JoinColumn(name = "current_round_id")
    val currentRound: TournamentRound? = null,

    @OneToMany(mappedBy = "tournament")
    val tableSeatings: List<TableSeating> = mutableListOf(),
)
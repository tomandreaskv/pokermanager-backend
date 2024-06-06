package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.blindstructure.BlindStructure
import no.vingaas.pokermanager.entities.stack.StartingStack
import java.time.LocalDateTime

@Entity
@Table(name = "tournament_settings", schema = "pokerman")
data class TournamentSpecification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_type_id", nullable = false)
    val tournamentType: TournamentType,

    @Column(name = "buy_in", nullable = false)
    val buyIn: Double?,

    @Column(name = "free_to_play", nullable = false)
    val freeToPlay: Boolean,

    @ManyToOne
    @JoinColumn(name = "blind_structure_id")
    val blindStructure: BlindStructure?,

    @ManyToOne
    @JoinColumn(name = "starting_stack_id")
    val startingStack: StartingStack?,

    val bounty: Long? = null,

    @Column(name = "max_rebuys")
    val maxRebuys: Int? = null,

    @Column(name = "rebuy_end_time")
    val rebuyEndTime: LocalDateTime? = null,

    @Column(name = "late_registration_period")
    val lateRegistrationPeriod: Int? = null,

    @Column(name = "guaranteed_prize_pool")
    val guaranteedPrizePool: Double? = null,

    @Column(name = "max_participants")
    val maxParticipants: Int? = null,

    @OneToMany(mappedBy = "specification")
    val prizeDistribution: List<PrizeDistribution> = mutableListOf()
)
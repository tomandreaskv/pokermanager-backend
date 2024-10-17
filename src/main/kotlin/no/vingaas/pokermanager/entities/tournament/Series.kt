package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "series", schema = "pokerman")
data class Series(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "series_name", unique = true)
    val name: String,
    val description: String,

    @ManyToOne
    @JoinColumn(name = "created_by")
    val createdBy: User,

    @Column(name = "prize_pool_percentage")
    val prizePoolPercentage: Double? = null,

    @Column(name = "accumulated_prize_pool")
    val accumulatedPrizePool: Double? = null,

    @ManyToOne
    @JoinColumn(name = "final_tournament_id")
    val finalTournament: Tournament? = null,

    @OneToMany(mappedBy = "series", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val tournaments: List<Tournament> = mutableListOf(),

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime
)
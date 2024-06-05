package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.equipment.PokerTable
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "table_balance_logs")
data class TableBalanceLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,

    @ManyToOne
    @JoinColumn(name = "from_table_id", nullable = false)
    val fromTable: PokerTable,

    @ManyToOne
    @JoinColumn(name = "to_table_id", nullable = false)
    val toTable: PokerTable,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val seatNumber: Int,

    @Column(nullable = false)
    val movedAt: LocalDateTime
)
package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.equipment.PokerTable
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "table_seating")
data class TableSeating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    val table: PokerTable,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(nullable = false)
    val seatNumber: Int,

    @Column(nullable = false)
    val createdAt: LocalDateTime,

    @Column(nullable = false)
    val updatedAt: LocalDateTime,

    @Column(nullable = false)
    val isDealer: Boolean = false
)
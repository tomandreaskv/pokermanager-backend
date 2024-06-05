package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.equipment.Equipment

@Entity
@Table(name = "tournament_equipments")
data class TournamentEquipment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    val tournament: Tournament,

    @ManyToOne
    @JoinColumn(name = "equipment_id", nullable = false)
    val equipment: Equipment
)
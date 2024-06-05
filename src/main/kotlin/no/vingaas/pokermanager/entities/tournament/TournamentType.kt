package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*

@Entity
@Table(name = "tournament_types", schema = "pokerman")
data class TournamentType(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "type_name", unique = true)
    val name: String,
    val description: String
)
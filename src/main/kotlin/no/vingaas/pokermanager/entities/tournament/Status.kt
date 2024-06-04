package no.vingaas.pokermanager.entities.tournament

import jakarta.persistence.*

@Entity
@Table(name = "statuses")
data class Status(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column( name = "status_name", unique = true)
    val name: String,
    val description: String
)
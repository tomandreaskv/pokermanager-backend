package no.vingaas.pokermanager.entities.common

import jakarta.persistence.*

@Entity
@Table(name = "statuses", schema = "pokerman")
data class Status(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column( name = "status_name", unique = true)
    val name: String,
    val description: String
)
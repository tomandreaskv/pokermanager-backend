package no.vingaas.pokermanager.entities.user

import jakarta.persistence.*

@Entity
@Table(name = "roles", schema = "pokerman")
data class UserRole(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "role_name", unique = true)
    val name: String,
    val description: String
)

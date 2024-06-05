package no.vingaas.pokermanager.security

import jakarta.persistence.*

@Entity
@Table(name = "admin_permissions")
data class AdminPermission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name ="permission_name", nullable = false)
    val permissionName: String,

    val description: String?
)
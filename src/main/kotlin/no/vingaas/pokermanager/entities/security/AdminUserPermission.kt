package no.vingaas.pokermanager.entities.security

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.user.User

@Entity
@Table(name = "admin_user_permissions")
data class AdminUserPermission(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @ManyToOne
    @JoinColumn(name = "admin_permission_id", nullable = false)
    val adminPermission: AdminPermission
)
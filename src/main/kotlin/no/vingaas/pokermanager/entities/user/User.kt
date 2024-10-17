package no.vingaas.pokermanager.entities.user

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.security.AdminPermission
import java.time.LocalDateTime

@Entity
@Table(name = "users", schema = "pokerman")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val username: String,
    @Column(name = "email", unique = true)
    val email: String,

    @Column(name = "is_admin")
    val isAdmin: Boolean,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime,

    @ManyToMany
    @JoinTable(
        name = "admin_user_permissions",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "admin_permission_id")]
    )
    val adminPermissions: List<AdminPermission> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_detail_id", referencedColumnName = "id")
    val userDetail: UserDetail,

    @ManyToOne
    @JoinColumn(name = "role_id")
    val role: UserRole
)

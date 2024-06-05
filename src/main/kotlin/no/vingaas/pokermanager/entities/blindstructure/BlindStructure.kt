package no.vingaas.pokermanager.entities.blindstructure

import jakarta.persistence.*
import no.vingaas.pokermanager.entities.user.User
import java.time.LocalDateTime

@Entity
@Table(name = "blind_structures" , schema = "pokerman")
data class BlindStructure(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "blind_structures_name", nullable = false)
    val blindStructuresName: String,

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    val createdBy: User,

    @OneToMany(mappedBy = "blindStructure", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val levels: List<Level> = emptyList(),

    @Column( name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column( name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime
)
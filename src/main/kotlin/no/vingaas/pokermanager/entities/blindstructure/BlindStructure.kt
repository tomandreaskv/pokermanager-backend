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
    var blindStructuresName: String,

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    val createdBy: User,

    @OneToMany(mappedBy = "blindStructureId", cascade = [CascadeType.ALL], orphanRemoval = true)
    val levels: List<Level> = mutableListOf(),

    @Column( name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column( name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime
)
package no.vingaas.pokermanager.entities.blindstructure

import jakarta.persistence.*

@Entity
@Table(name = "levels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "level_type", discriminatorType = DiscriminatorType.STRING)
sealed class Level(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blind_structure_id", nullable = false)
    open val blindStructure: BlindStructure,

    @Column(name="level_order",nullable = false)
    open val levelOrder: Int,

    @Column(nullable = false)
    open val duration: Int
)

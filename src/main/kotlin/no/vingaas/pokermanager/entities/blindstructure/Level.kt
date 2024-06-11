package no.vingaas.pokermanager.entities.blindstructure

import jakarta.persistence.*

@Entity
@Table(name = "levels", schema = "pokerman")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "level_type", discriminatorType = DiscriminatorType.STRING)
open class Level(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0,

    @Column(name = "blind_structure_id", nullable = false)
    open val blindStructureId: Long,

    @Column(name="level_order",nullable = false)
    open val levelOrder: Int,

    @Column(nullable = false)
    open val duration: Int
)

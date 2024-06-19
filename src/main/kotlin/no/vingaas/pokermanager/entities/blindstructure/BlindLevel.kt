package no.vingaas.pokermanager.entities.blindstructure

import jakarta.persistence.*

@Entity
@DiscriminatorValue("BlindLevel")
data class BlindLevel(
    override val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    override val blindStructure: BlindStructure,
    override val levelOrder: Int,
    override val duration: Int,

    @Column(name = "small_blind", nullable = false)
    val smallBlind: Int,

    @Column(name = "big_blind", nullable = false)
    val bigBlind: Int,

    @Column(name = "ante")
    val ante: Int?
) : Level(id, blindStructure, levelOrder, duration)
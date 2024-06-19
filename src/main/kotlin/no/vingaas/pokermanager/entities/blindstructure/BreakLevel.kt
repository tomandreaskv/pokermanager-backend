package no.vingaas.pokermanager.entities.blindstructure

import jakarta.persistence.*

@Entity
@DiscriminatorValue("BreakLevel")
data class BreakLevel(
    override val id: Long = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    override val blindStructure: BlindStructure,
    override val levelOrder: Int,
    override val duration: Int,

    @Column(name = "color_up", nullable = false)
    val colorUp: Boolean
) : Level(id, blindStructure, levelOrder, duration)
package no.vingaas.pokermanager.entities.chip

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "chip_values", schema = "pokerman")
data class ChipValue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "chip_set_id", nullable = false)
    val chipSet: ChipSet,

    @Column( name = "chip_value", nullable = false)
    val chipValue: Int,

    @Column(nullable = false)
    val quantity: Int,

    // Fysiske egenskaper
    @Column(name = "material")
    val material: String? = null,  // Materialet chippen er laget av (f.eks. clay, plastic)

    @Column(name = "color")
    val color: String? = null,  // Farge på chippen

    @Column(name = "weight", precision = 5, scale = 2)
    val weight: BigDecimal? = null,  // Vekt i gram, med to desimaler for presisjon

    @Column(name = "diameter", precision = 5, scale = 2)
    val diameter: BigDecimal? = null,  // Diameter i millimeter, med to desimaler for presisjon
)
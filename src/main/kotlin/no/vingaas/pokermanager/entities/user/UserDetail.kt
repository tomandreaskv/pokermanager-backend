package no.vingaas.pokermanager.entities.user

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "user_detail", schema = "pokerman")
data class UserDetail(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "first_name")
    val firstname: String,
    @Column(name = "last_name")
    val lastname: String,
    @Column(name = "date_of_birth")
    val dateOfBirth: LocalDate,
    @Column(name = "country")
    val country: String,
    @Column(name = "phone_number")
    val phoneNumber: String?,
    @Column(name = "city")
    val city: String?,
    @Column(name = "address")
    val address: String?,
    @Column(name = "zip_code")
    val zipCode: String?,
    @Column(name = "bio")
    val bio: String?,
    @Column(name = "profile_picture_url")
    val profilePicture: String?
)
{

}
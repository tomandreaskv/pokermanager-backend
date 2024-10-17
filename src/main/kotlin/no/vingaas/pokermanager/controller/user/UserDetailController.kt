package no.vingaas.pokermanager.controller.user

import no.vingaas.pokermanager.config.CustomUserDetails
import no.vingaas.pokermanager.dto.user.UpdateUserDetailDTO
import no.vingaas.pokermanager.dto.user.UserDetailDTO
import no.vingaas.pokermanager.entities.user.UserDetail
import no.vingaas.pokermanager.service.user.UserDetailService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user/details")
class UserDetailController(
    private val userDetailService: UserDetailService
) {
    private val logger = LoggerFactory.getLogger(UserDetailController::class.java)

    @GetMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    fun getUserDetailById(@PathVariable id: Long, authentication: Authentication): ResponseEntity<UserDetailDTO> {
        val currentUser = authentication.principal as CustomUserDetails
        val userDetail = userDetailService.getDetailsById(id)

        return if (currentUser.getId() == id || currentUser.isAdmin) {
            val userDetailDTO = UserDetailDTO(
                firstname = userDetail.firstname,
                lastname = userDetail.lastname,
                dateOfBirth = userDetail.dateOfBirth,
                country = userDetail.country,
                phoneNumber = userDetail.phoneNumber,
                city = userDetail.city,
                address = userDetail.address,
                zipCode = userDetail.zipCode,
                bio = userDetail.bio,
                profilePicture = userDetail.profilePicture
            )
            ResponseEntity.ok(userDetailDTO)
        } else {
            ResponseEntity.status(403).build()
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    fun updateUserDetail(
        @PathVariable id: Long,
        @RequestBody updateUserDetailDTO: UpdateUserDetailDTO,
        authentication: Authentication
    ): ResponseEntity<UserDetail> {
        val currentUser = authentication.principal as CustomUserDetails

        if (currentUser.getId() == id || currentUser.isAdmin) {
            val userDetail = userDetailService.getDetailsById(id).copy(
                firstname = updateUserDetailDTO.firstname,
                lastname = updateUserDetailDTO.lastname,
                phoneNumber = updateUserDetailDTO.phoneNumber,
                city = updateUserDetailDTO.city,
                address = updateUserDetailDTO.address,
                zipCode = updateUserDetailDTO.zipCode,
                bio = updateUserDetailDTO.bio,
                profilePicture = updateUserDetailDTO.profilePicture
            )
            val updatedDetail = userDetailService.update(userDetail)
            return ResponseEntity.ok(updatedDetail)
        } else {
            return ResponseEntity.status(403).build()
        }
    }
}
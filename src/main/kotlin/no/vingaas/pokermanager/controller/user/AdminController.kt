package no.vingaas.pokermanager.controller.user

import no.vingaas.pokermanager.dto.user.UpdateUserRoleDTO
import no.vingaas.pokermanager.service.user.UserCredentialService
import no.vingaas.pokermanager.service.user.UserRoleService
import no.vingaas.pokermanager.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController (
    private val userCredentialService: UserCredentialService,
    private val userService: UserService,
    private val userRoleService: UserRoleService
) {

    // Endepunkt for å opprette midlertidig passord for en bruker
    @PreAuthorize("hasRole('ADMIN')") // Sørg for at bare administratorer har tilgang
    @PostMapping("/users/{userId}/temporary-password")
    fun createTemporaryPassword(@PathVariable userId: Long): ResponseEntity<String> {
        val temporaryPassword = userCredentialService.createTemporaryPassword(userId)
        return ResponseEntity.ok("Det midlertidige passordet for brukeren er: $temporaryPassword")
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{userId}/make-admin")
    fun makeUserAdmin(@PathVariable userId: Long): ResponseEntity<String> {
        val user = userService.findById(userId)
            ?: return ResponseEntity.notFound().build()

        if (user.isAdmin) {
            return ResponseEntity.badRequest().body("Brukeren er allerede en administrator.")
        }

        // Hent rollen "ADMIN"
        val adminRole = userRoleService.findByName("ADMIN")
            ?: return ResponseEntity.badRequest().body("Admin-rolle ikke funnet.")

        // Oppdater brukerens rolle og isAdmin-status
        val updatedUser = user.copy(
            isAdmin = true,
            role = adminRole  // Oppdater brukerens rolle til "ADMIN"
        )
        userService.update(updatedUser)

        return ResponseEntity.ok("Brukeren med ID $userId har blitt oppgradert til administrator.")
    }

    @PutMapping("users/role")
    @PreAuthorize("hasRole('ADMIN')")  // Kun admin kan endre brukerens rolle
    fun updateUserRole(@RequestBody updateUserRoleDTO: UpdateUserRoleDTO): ResponseEntity<String> {
        val user = userService.findById(updateUserRoleDTO.userId)
            ?: return ResponseEntity.notFound().build()

        // Sjekk om brukeren allerede har den gamle rollen
        if (user.role.name != updateUserRoleDTO.oldRole) {
            return ResponseEntity.badRequest().body("The user's current role does not match the provided old role.")
        }

        // Hent ny rolle basert på rollenavnet som sendes inn
        val newRole = userRoleService.findByName(updateUserRoleDTO.newRole)
            ?: return ResponseEntity.badRequest().body("Invalid role")

        // Oppdater brukerens rolle
        userService.updateUserRole(user, newRole)

        return ResponseEntity.ok("User role updated successfully from ${updateUserRoleDTO.oldRole} to ${updateUserRoleDTO.newRole} for user ${updateUserRoleDTO.username}")
    }
}
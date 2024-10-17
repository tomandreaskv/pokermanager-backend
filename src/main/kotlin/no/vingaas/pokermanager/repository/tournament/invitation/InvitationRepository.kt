package no.vingaas.pokermanager.repository.tournament.invitation

import no.vingaas.pokermanager.entities.tournament.invitation.Invitation
import org.springframework.data.jpa.repository.JpaRepository

interface InvitationRepository : JpaRepository<Invitation, Long> {
}
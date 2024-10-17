package no.vingaas.pokermanager.repository.notification

import no.vingaas.pokermanager.entities.notification.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {
}
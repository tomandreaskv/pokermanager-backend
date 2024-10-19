package no.vingaas.pokermanager.entities.tournament

enum class TournamentVisibility {
    PUBLIC,   // Alle kan se og delta
    CLOSED,   // Alle kan se, men må inviteres eller be om å delta
    PRIVATE   // Kun inviterte kan se og delta
}
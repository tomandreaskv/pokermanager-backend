package no.vingaas.pokermanager.common.request

data class AuthenticationRequest(
    val email : String,
    val password : String
) {

}

package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserCredential
import org.springframework.stereotype.Service

@Service
interface UserCredentialService {

        fun getCredentialsById(id: Long): UserCredential
        fun getCredentialsByUsername(username: String): UserCredential
        fun save(userCredential: UserCredential): UserCredential
        fun update(userCredential: UserCredential): UserCredential
        fun delete(userCredential: UserCredential)
}
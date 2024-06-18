package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserCredential
import org.springframework.stereotype.Service
import java.util.*

@Service
interface UserCredentialService {

        fun getCredentialsById(id: Long): UserCredential
        fun getCredentialsByUsername(username: String): UserCredential
        fun getCredentialsByUserId(userId: Long): Optional<UserCredential>
        fun save(userCredential: UserCredential): UserCredential
        fun update(userCredential: UserCredential): UserCredential
        fun delete(userCredentialId: Long): Boolean
}
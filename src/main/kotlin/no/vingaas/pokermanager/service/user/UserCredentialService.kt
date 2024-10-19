package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserCredential
import org.springframework.stereotype.Service

@Service
interface UserCredentialService {

        fun getCredentialsById(id: Long): UserCredential
        fun getCredentialsByUserId(userId: Long): UserCredential
        fun save(userCredential: UserCredential): UserCredential
        fun update(userCredential: UserCredential): UserCredential
        fun delete(userCredentialId: Long): Boolean
        fun createTemporaryPassword(userId: Long): String
        fun validateTemporaryPassword(userId: Long, password: String): Boolean
        fun updatePassword(userId: Long, newPassword: String)
}
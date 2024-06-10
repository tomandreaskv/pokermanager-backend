package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserDetail
import org.springframework.stereotype.Service

@Service
interface UserDetailService {

    fun getDetailsById(id: Long): UserDetail
    fun save(userDetail: UserDetail): UserDetail
    fun update(userDetail: UserDetail): UserDetail
    fun delete(userDetail: UserDetail)

}
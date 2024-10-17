package no.vingaas.pokermanager.service.user

import no.vingaas.pokermanager.entities.user.UserDetail
import no.vingaas.pokermanager.repository.user.UserDetailRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserDetailServiceImpl(private val userDetailRepository: UserDetailRepository) : UserDetailService {
    private val logger = LoggerFactory.getLogger(UserDetailServiceImpl::class.java)
    override fun getDetailsById(id: Long): UserDetail {
        logger.info("Finding user detail by id: $id")
        return userDetailRepository.findById(id).orElseThrow {
            logger.error("User detail not found")
            throw IllegalArgumentException("User detail not found") }

    }

    override fun save(userDetail: UserDetail): UserDetail {
        logger.info("Saving user detail")
        return userDetailRepository.save(userDetail)
    }

    override fun update(userDetail: UserDetail): UserDetail {
        logger.info("Updating user detail")
        return userDetailRepository.save(userDetail)
    }

    override fun delete(userDetail: UserDetail) {
        logger.info("Deleting user detail")
        userDetailRepository.delete(userDetail)
    }
}
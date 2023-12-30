package com.kjj.user.service.manager

import com.kjj.user.dto.info.ManagerInfoDto
import com.kjj.user.exception.CantFindByIdException
import com.kjj.user.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ManagerService(
    val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun getInfo(username: String): ManagerInfoDto {
        val user = userRepository.findByUsername(username)
            ?: throw CantFindByIdException(
                    """
                    username을 가진 유저 데이터가 없습니다.
                    username : $username
                    """
            )

        return ManagerInfoDto.from(user)
    }
}
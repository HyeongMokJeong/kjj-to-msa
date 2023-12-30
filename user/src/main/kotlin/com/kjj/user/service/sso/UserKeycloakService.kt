package com.kjj.user.service.sso

import com.kjj.user.dto.user.JoinDto
import com.kjj.user.dto.user.UserDto
import com.kjj.user.entity.User
import com.kjj.user.entity.UserMypage
import com.kjj.user.entity.UserPolicy
import com.kjj.user.exception.CantFindByIdException
import com.kjj.user.repository.user.UserMyPageRepository
import com.kjj.user.repository.user.UserPolicyRepository
import com.kjj.user.repository.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserKeycloakService(
    val userRepository: UserRepository,
    val userPolicyRepository: UserPolicyRepository,
    val userMyPageRepository: UserMyPageRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
) {
    private fun createNewUserWithDefaultPolicyAndMyPage(dto: JoinDto): User {
        val userPolicy = userPolicyRepository.save(UserPolicy.createNewUserPolicy())
        val userMyPage = userMyPageRepository.save(UserMypage.createNewUserMyPage())
        return User.of(dto, userPolicy, userMyPage)
    }

    private fun createNewUserWithDefaultPolicyAndMyPage(dto: UserDto): User {
        val userPolicy = userPolicyRepository.save(UserPolicy.createNewUserPolicy())
        val userMyPage = userMyPageRepository.save(UserMypage.createNewUserMyPage())
        return User.of(dto, userPolicy, userMyPage)
    }

    fun findByUsername(username: String): UserDto {
        val user: User = userRepository.findByUsername(username)
            ?: throw CantFindByIdException(
                """
                해당 username을 가진 유저를 찾을 수 없습니다.
                username = $username
                """
            )
        val result = UserDto.from(user)
        user.updateLoginDate()
        return result
    }

    fun existsByUsername(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    @Transactional
    fun join(dto: JoinDto): Boolean {
        dto.setEncodePassword(bCryptPasswordEncoder)
        try {
            userRepository.save(createNewUserWithDefaultPolicyAndMyPage(dto))
        } catch (e: Exception) {
            return false
        }
        return true
    }

    @Transactional
    fun joinKeycloak(dto: UserDto): UserDto? {
        try {
            userRepository.save(createNewUserWithDefaultPolicyAndMyPage(dto))
        } catch (e: java.lang.Exception) {
            return null
        }
        return dto
    }
}
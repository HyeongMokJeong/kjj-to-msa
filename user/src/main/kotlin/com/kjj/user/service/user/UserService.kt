package com.kjj.user.service.user

import com.kjj.user.client.MenuClient
import com.kjj.user.dto.info.UserInfoDto
import com.kjj.user.dto.user.DatePolicyDto
import com.kjj.user.dto.user.MyPageDto
import com.kjj.user.dto.user.PolicyDto
import com.kjj.user.dto.user.UsePointDto
import com.kjj.user.entity.User
import com.kjj.user.entity.UserMypage
import com.kjj.user.entity.UserPolicy
import com.kjj.user.exception.CantFindByIdException
import com.kjj.user.exception.CantFindByUsernameException
import com.kjj.user.exception.WrongRequestBodyException
import com.kjj.user.repository.user.UserPolicyRepository
import com.kjj.user.repository.user.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
    val userPolicyRepository: UserPolicyRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder,
    val menuClient: MenuClient
) {
    @Transactional(readOnly = true)
    fun getInfo(id: Long): UserInfoDto {
        val user = userRepository.findById(id).orElseThrow {
            CantFindByIdException(
                """
                해당 id를 가진 유저를 찾을 수 없습니다.
                id : $id
                """
            )
        }
        return UserInfoDto.from(user)
    }

    @Transactional(readOnly = true)
    fun getMyPage(id: Long): MyPageDto {
        val myPage: UserMypage = userRepository.findByIdJoinMyPage(id)?.userMypage
            ?: throw CantFindByIdException(
                """
                해당 id를 가진 유저를 찾을 수 없습니다.
                id : $id
                """
            )
        return MyPageDto.from(myPage)
    }

    @Transactional
    fun usePoint(id: Long, dto: UsePointDto): Int {
        val myPage: UserMypage = userRepository.findByIdJoinMyPage(id)?.userMypage
            ?: throw CantFindByIdException(
                """
                해당 id를 가진 유저를 찾을 수 없습니다.
                id : $id
                """
            )

        val data = -1 * dto.value
        if (myPage.point + data < 0) throw WrongRequestBodyException(
            """
            포인트가 부족합니다.
            point : $myPage.point
            """
        )
        myPage.updatePoint(data)
        return myPage.point
    }

    @Transactional
    fun setUserDatePolicy(dto: DatePolicyDto, id: Long): PolicyDto {
        val policy: UserPolicy = userRepository.findByIdJoinPolicy(id)?.userPolicy
            ?: throw CantFindByIdException(
                """
                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
                id : $id
                """
            )
        policy.setPolicy(dto)
        return PolicyDto.from(policy)
    }

    @Transactional
    fun setUserMenuPolicy(userId: Long, menuId: Long): PolicyDto {
        if (!menuClient.existsById(menuId)) throw WrongRequestBodyException(
            """
                menuId를 가진 메뉴 데이터가 존재하지 않습니다.
                menuId : $menuId
                """
        )
        val policy: UserPolicy = userRepository.findByIdJoinPolicy(userId)?.userPolicy
            ?: throw CantFindByIdException(
                """
                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
                id : $userId
                """
            )
        policy.setDefaultMenu(menuId)
        return PolicyDto.from(policy)
    }

    @Transactional(readOnly = true)
    fun getUserPolicy(id: Long): PolicyDto {
        val policy: UserPolicy = userRepository.findByIdJoinPolicy(id)?.userPolicy
            ?: throw CantFindByIdException(
                """
                해당 id를 가진 유저 데이터를 찾을 수 없습니다.
                id : $id
                """
            )
        return PolicyDto.from(policy)
    }

    @Transactional
    fun withdraw(username: String): Boolean {
        val user: User = userRepository.findByUsername(username) ?: return false
        userRepository.delete(user)
        return true
    }

    fun checkPassword(username: String, password: String?): Boolean {
        val user: User = userRepository.findByUsername(username)
            ?: throw CantFindByUsernameException(
                """
                해당 username을 가진 유저 데이터를 찾을 수 없습니다.
                username = $username
                """
            )
        return bCryptPasswordEncoder.matches(password, user.password)
    }

    fun clearPolicyByDeletedMenu(menuId: Long) {
        userPolicyRepository.clearPolicyByDeletedMenu(menuId)
    }
}
package com.kjj.user.repository.user

import com.kjj.user.entity.UserPolicy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserPolicyRepository: JpaRepository<UserPolicy, Long> {
    @Query("update UserPolicy up set up.defaultMenu = null where up.defaultMenu = :menuId")
    fun clearPolicyByDeletedMenu(menuId: Long)
}
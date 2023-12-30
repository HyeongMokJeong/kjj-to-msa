package com.kjj.user.repository.user

import com.kjj.user.entity.UserMypage
import org.springframework.data.jpa.repository.JpaRepository

interface UserMyPageRepository: JpaRepository<UserMypage, Long> {
}
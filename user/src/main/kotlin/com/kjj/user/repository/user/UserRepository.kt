package com.kjj.user.repository.user

import com.kjj.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun existsByUsername(username: String): Boolean

    @Query("select u from User u join fetch u.userPolicy where u.id = :id")
    fun findByIdJoinPolicy(id: Long): User?

    @Query("select u from User u join fetch u.userMypage where u.id = :id")
    fun findByIdJoinMyPage(id: Long): User?
}
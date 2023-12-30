package com.kjj.user.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class UserMypage(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val id: Long?,
   var point: Int
) {
    companion object {
        fun createNewUserMyPage(): UserMypage {
            return UserMypage(
                null,
                0
            )
        }
    }

    fun updatePoint(value: Int) {
        point += value
    }
}
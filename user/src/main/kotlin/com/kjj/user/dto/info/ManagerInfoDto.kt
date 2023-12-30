package com.kjj.user.dto.info

import com.kjj.user.entity.User

data class ManagerInfoDto (
    val id: Long,
    val username: String
){
    // java의 static method
    companion object {
        fun from(manager: User): ManagerInfoDto {
            return ManagerInfoDto(
                id = manager.id!!,
                username = manager.username
            )
        }
    }
}
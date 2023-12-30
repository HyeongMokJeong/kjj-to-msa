package com.kjj.user.entity

import com.kjj.user.dto.user.JoinDto
import com.kjj.user.dto.user.UserDto
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @OneToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE))
    val userPolicy: UserPolicy,

    @OneToOne(fetch = FetchType.LAZY, cascade = arrayOf(CascadeType.REMOVE))
    val userMypage: UserMypage,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = arrayOf(CascadeType.REMOVE))
    val order: List<Order>?,

    val username: String,
    val password: String,
    val roles: String,
    var loginDate: LocalDate?
) {
    companion object {
        fun of(dto: JoinDto, userPolicy: UserPolicy, userMypage: UserMypage): User {
            return User(
                null,
                userPolicy,
                userMypage,
                null,
                dto.username,
                dto.password,
                "ROLE_USER",
                null
            )
        }

        fun of(dto: UserDto, userPolicy: UserPolicy, userMypage: UserMypage): User {
            return User(
                null,
                userPolicy,
                userMypage,
                null,
                dto.username,
                dto.password,
                dto.roles,
                null
            )
        }
    }

    fun updateLoginDate() {
        loginDate = LocalDate.now()
    }
}
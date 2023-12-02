package com.kjj.user.entity;

import com.kjj.user.dto.user.JoinDto;
import com.kjj.user.dto.user.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private UserPolicy userPolicy;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private UserMyPage userMypage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> order;

    private String username;
    private String password;
    private String roles;
    private LocalDate loginDate;

    public static User of(JoinDto dto, UserPolicy userPolicy, UserMyPage userMypage) {
        return new User(
                null,
                userPolicy,
                userMypage,
                null,
                dto.getUsername(),
                dto.getPassword(),
                "ROLE_USER",
                null
        );
    }

    public static User of(UserDto dto, UserPolicy userPolicy, UserMyPage userMypage) {
        return new User(
                null,
                userPolicy,
                userMypage,
                null,
                dto.getUsername(),
                dto.getPassword(),
                dto.getRoles(),
                null
        );
    }

    public void setUserPolicy(UserPolicy userPolicy) {
        this.userPolicy = userPolicy;
    }
    public void setUserMypage(UserMyPage userMypage) {
        this.userMypage = userMypage;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void updateLoginDate() {
        loginDate = LocalDate.now();
    }
}

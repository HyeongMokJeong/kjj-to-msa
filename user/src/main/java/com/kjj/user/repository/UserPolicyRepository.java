package com.kjj.user.repository;

import com.kjj.user.entity.User;
import com.kjj.user.entity.UserPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPolicyRepository extends JpaRepository<UserPolicy, Long> {
    List<UserPolicy> findAllByDefaultMenu(Long menuId);
}

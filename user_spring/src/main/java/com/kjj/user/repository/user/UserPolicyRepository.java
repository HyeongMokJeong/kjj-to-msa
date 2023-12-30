package com.kjj.user.repository.user;

import com.kjj.user.entity.UserPolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserPolicyRepository extends JpaRepository<UserPolicy, Long> {

    @Query("UPDATE UserPolicy up SET up.defaultMenu = null WHERE up.defaultMenu = :menuId")
    void clearPolicyByDeletedMenu(Long menuId);
}

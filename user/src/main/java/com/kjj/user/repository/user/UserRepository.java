package com.kjj.user.repository.user;

import com.kjj.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("select u from User u join fetch u.userPolicy where u.id = :id")
    Optional<User> findByIdWithFetchPolicy(Long id);

    @Query("select u from User u join fetch u.userMypage where u.id = :id")
    Optional<User> findByIdWithFetchMyPage(Long id);
}

package com.kjj.auth.repository;

import com.kjj.auth.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByUsername(String username) {
        String stringQuery = "SELECT id, username, password, roles FROM user WHERE username = ?";
        Query query = entityManager.createNativeQuery(stringQuery).setParameter(1, username);
        return User.from((Object[]) query.getSingleResult());
    }
}

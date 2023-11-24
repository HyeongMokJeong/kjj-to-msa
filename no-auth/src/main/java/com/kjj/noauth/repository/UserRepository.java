package com.kjj.noauth.repository;

import com.kjj.noauth.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
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

    public boolean existsByUsername(String username) {
        String stringQuery = "SELECT id FROM user WHERE username = ?";
        Query query = entityManager.createNativeQuery(stringQuery).setParameter(1, username);
        try {
            query.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
}

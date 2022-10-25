package com.app.repository;

import com.app.model.Role;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select user from User user where user.deleted = false" )
    List<User> findAll();

    @Query("select user from User user where user.username = ?1 and user.deleted = false" )
    User findByUsername(String username);

    @Query("select user from User user where user.role.name = ?1 and user.deleted = false" )
    List<User> findAllByRole(String role);
}

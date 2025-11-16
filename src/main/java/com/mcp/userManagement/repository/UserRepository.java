package com.mcp.userManagement.repository;

import com.mcp.userManagement.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users WHERE u.isDeleted = 0 AND u.username = :username")
    Optional<Users> findByUsername(@Param("username") String username);
}

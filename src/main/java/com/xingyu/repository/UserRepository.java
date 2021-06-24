package com.xingyu.repository;

import com.xingyu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM sys_user user WHERE user.username = ?1")
    User findByUsername(String username);
}

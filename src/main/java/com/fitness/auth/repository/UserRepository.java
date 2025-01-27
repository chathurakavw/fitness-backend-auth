package com.fitness.auth.repository;

import com.fitness.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByDeletedFalse();
    Optional<User> findUserByUsername(String name);
}

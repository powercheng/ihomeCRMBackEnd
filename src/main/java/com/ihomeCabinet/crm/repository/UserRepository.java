package com.ihomeCabinet.crm.repository;

import com.ihomeCabinet.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findByRegion(Integer region);

    boolean existsByName(String name);
}
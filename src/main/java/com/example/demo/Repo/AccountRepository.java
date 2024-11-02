package com.example.demo.Repo;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}

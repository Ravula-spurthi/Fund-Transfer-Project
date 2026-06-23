package com.fundtransfer.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.User;
 
public interface UserRepository extends JpaRepository<User, Long> {
 
    User findByEmail(String email);
 
}
 
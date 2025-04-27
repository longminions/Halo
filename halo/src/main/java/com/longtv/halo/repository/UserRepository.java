package com.longtv.halo.repository;

import com.longtv.halo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByEmail(String email);

    User findOneUsersById(Long id);
}

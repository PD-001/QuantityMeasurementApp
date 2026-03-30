package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByGoogleId(String googleId);

    Optional<UserEntity> findByEmail(String email);
}
package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.UserDto;
import org.inventory.magedsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
}

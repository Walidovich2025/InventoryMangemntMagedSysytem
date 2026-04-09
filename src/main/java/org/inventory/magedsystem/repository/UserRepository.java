package org.inventory.magedsystem.repository;

import org.inventory.magedsystem.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDto,Long> {

    Optional<UserDto> findByEmail(String email);
}

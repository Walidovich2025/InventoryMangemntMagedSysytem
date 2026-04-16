package org.inventory.magedsystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.inventory.magedsystem.dto.LoginRequest;
import org.inventory.magedsystem.dto.RegisterRequest;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.UserDto;
import org.inventory.magedsystem.entity.User;
import org.inventory.magedsystem.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> getallusers() {
        return ResponseEntity.ok(userService.getAllUsers());

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateuser(@PathVariable Long id,@RequestBody UserDto userDto) {
         return ResponseEntity.ok(userService.updateUser(id,userDto));

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> deleteuser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));

    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<Response> getUserTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserTransaction(userId));

    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentloggedinUser());

    }


}

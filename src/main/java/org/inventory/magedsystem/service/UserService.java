package org.inventory.magedsystem.service;

import org.inventory.magedsystem.dto.LoginRequest;
import org.inventory.magedsystem.dto.RegisterRequest;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.UserDto;
import org.inventory.magedsystem.entity.User;

public interface UserService {
    Response registerUser(RegisterRequest registerRequest);
    Response LoginUser(LoginRequest loginRequest);
     User getCurrentloggedinUser();
     Response getAllUsers();
     Response updateUser(Long id , UserDto userDto);
    Response deleteUser(Long id);
    Response getUserTransaction(Long id);


}

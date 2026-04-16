package org.inventory.magedsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.inventory.magedsystem.dto.LoginRequest;
import org.inventory.magedsystem.dto.RegisterRequest;
import org.inventory.magedsystem.dto.Response;
import org.inventory.magedsystem.dto.UserDto;
import org.inventory.magedsystem.entity.User;
import org.inventory.magedsystem.enums.UserRole;
import org.inventory.magedsystem.exceptions.InvalidCarednitalsException;
import org.inventory.magedsystem.exceptions.NotFoundException;
import org.inventory.magedsystem.repository.UserRepository;
import org.inventory.magedsystem.security.JWTUtils;
import org.inventory.magedsystem.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class userServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final ModelMapper modelMapper;
    private  final JWTUtils jwtUtils;




    @Override
    public Response registerUser(RegisterRequest registerRequest) {
        UserRole role=UserRole.MANGER;
        if(registerRequest.getRole()!=null){
            role=registerRequest.getRole();
        }
        User userToSave=User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode((registerRequest.getPassword())))
                .phone_number(registerRequest.getPhone_number())
                .role(role)
                .build();
        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("user registered successfully")
                .build();
    }

    @Override
    public Response LoginUser(LoginRequest loginRequest) {

        User user=userRepository.findByEmail((loginRequest.getEmail()))
                .orElseThrow(()->new NotFoundException("email not found"));
        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw new InvalidCarednitalsException("invalid password does not correct");


        }
        String token= jwtUtils.GenerateToken(user.getEmail());
        return  Response.builder()
                .status(200)
                .message("user logged in successfully")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 month")
                .build();
    }

    @Override
    public User getCurrentloggedinUser() {

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String email=authentication.getName();
        User user=userRepository.findByEmail(email)
                .orElseThrow(()->new NotFoundException("user not found"));
        user.setTransactions(null);
        return user;



    }

    @Override
    public Response getAllUsers() {

        List<User>users=userRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<UserDto>userDtos=modelMapper.map(users,
                new TypeToken<List<UserDto>>(){}.getType());

        userDtos.forEach(UserDto->UserDto.setTransactions(null));

        return Response.builder()
                .status(200)
                .users(userDtos)
                .build();
    }

    @Override
    public Response updateUser(Long id, UserDto userDto) {
        User existinguser = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));
        if (userDto.getEmail() != null) {
            existinguser.setEmail(userDto.getEmail());
        }
        if (userDto.getName() != null) {
            existinguser.setName(userDto.getName());
        }
        if (userDto.getPhonenumber() != null) {
            existinguser.setPhone_number(userDto.getPhonenumber());
        }
        if (userDto.getRole() != null) {
            existinguser.setRole(userDto.getRole());
        }
        if (userDto.getPassword() != null) {
            existinguser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(existinguser);
        return Response.builder()
                .status(200)
                .message("user updated successfully")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));
        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("user successfully deleted")
                .build();

    }

    @Override
    public Response getUserTransaction(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("user not found"));

        UserDto userDto1 = modelMapper.map(user, UserDto.class);
        userDto1.getTransactions().
                forEach(transactionDto ->{
                    transactionDto.setUserDto(null);
                    transactionDto.setSupplierDto(null);



                });

        return Response.builder()
                .status(200)
                .message("success")
                .user(userDto1)
                .build();


    }
}

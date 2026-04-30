package org.inventory.magedsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.magedsystem.entity.User;
import org.inventory.magedsystem.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long Id ;
    private  String name;
    private  String email;


    @JsonIgnore
    private String password;
    private String phonenumber;
    private UserRole role;
    private  List<TransactionDto> transactions;

    private  LocalDateTime createdAt;


}

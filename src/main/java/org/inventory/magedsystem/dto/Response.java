package org.inventory.magedsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.inventory.magedsystem.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    //
    private int status;
    private String message;
    //for login cardential
    private String token;
    private UserRole role;
    private String expirationTime;

    //for pagination
    private Integer totalPages;
    private long totalElements;
    //for output optional

    private UserDto user;
    private List<UserDto> users;

    private SupplierDto supplier;
    private List<SupplierDto> suppliers;

    private CategoryDto category;
    private List<CategoryDto> categories;

    private ProductDto product;
    private List<ProductDto> products;

    private TransactionDto transaction;
    private List<TransactionDto> transactions;

    private LocalDateTime timestamp = LocalDateTime.now();
}


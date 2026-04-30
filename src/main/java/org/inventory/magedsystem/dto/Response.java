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

    private UserDto userDto;
    private List<UserDto> userDtos;

    private SupplierDto supplierDto;
    private List<SupplierDto> supplierDtos;

    private CategoryDto categoryDto;
    private List<CategoryDto> categoriesDTO;

    private ProductDto productDto;
    private List<ProductDto> productDtos;

    private TransactionDto transactionDto;
    private List<TransactionDto> transactionDtos;

    private LocalDateTime timestamp = LocalDateTime.now();
}


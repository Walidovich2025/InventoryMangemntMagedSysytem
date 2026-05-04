package org.inventory.magedsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.magedsystem.enums.TransactionStatus;
import org.inventory.magedsystem.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {

   private Long id;


   private Integer totalProducts;
    private BigDecimal totalprices;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private  String description;
    private  LocalDateTime createdAt;
    private  LocalDateTime updatedAt;



    private UserDto user;

    private  ProductDto product;


    private  SupplierDto supplier;


}

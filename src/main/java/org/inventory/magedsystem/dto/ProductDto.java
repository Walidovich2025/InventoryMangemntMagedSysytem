package org.inventory.magedsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {


    private  Long id;
    private Long productId;
    private  Long categoryId;
    private Long supplierId;
    private String Name;
    private String sku;
    private BigInteger stockQunantity;

    private BigDecimal price;


    private  String description;
    private String imageUrl;

    private final LocalDateTime createdAt=LocalDateTime.now();
    private  LocalDateTime expiryDate;
    private  LocalDateTime updatedAt;




}

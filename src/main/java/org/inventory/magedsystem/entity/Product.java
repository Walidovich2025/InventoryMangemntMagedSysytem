package org.inventory.magedsystem.entity;

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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "sku is required")
    @Column(unique = true)
    private String sku;

    @Positive(message = "product price must be a positive value")
    private BigDecimal price;
    @Min(value = 0,message = "quentity must not be less than zero")
    private  BigInteger StockQuentity;
    private  String description;
    private String imageUrl;

    private LocalDateTime expiryDate;

    private LocalDateTime updatedAt;
    private final LocalDateTime createdAt=LocalDateTime.now();
     @ManyToOne
     @JoinColumn(name = "category_id")
    private Category category;

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", StockQuentity=" + StockQuentity +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", expiryDate=" + expiryDate +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                '}';
    }
}

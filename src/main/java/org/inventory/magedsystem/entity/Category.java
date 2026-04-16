package org.inventory.magedsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @NotBlank(message = "name is required")
    private String name;


    @OneToMany(mappedBy = "category")
    private List<Product> products;


    @Override
    public String toString() {
        return "Category{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }





}

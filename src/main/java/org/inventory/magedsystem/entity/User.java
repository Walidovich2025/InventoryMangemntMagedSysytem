package org.inventory.magedsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.inventory.magedsystem.enums.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @NotBlank(message = "name is required")
   // @Column(unique = true)
    private String name;



    @NotBlank(message = "name is required")
    private String password;
    @NotBlank(message = "email is required")
    @Column(unique = true)
    private String email;


    @NotBlank(message = "phone number is required")
    private String phone_number;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    private LocalDateTime createdAt=LocalDateTime.now();

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                '}';
    }
}

package com.example.demo.model.entity;

import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(schema = "users")
public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        private Long id;

        @Column
        private String firstName;
        @Column
        private String lastName;
        @Column(unique = true)
        private String email;
        @Column
        private String password;
        @Column(name = "phone_number", unique = true)
        private String phoneNumber;
        private LocalDateTime createAt;
         private boolean enabled;

        @OneToMany(mappedBy = "user")
        private List<Orders> orders;

        @OneToMany(mappedBy = "user")
        private List<Review> review;

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "address_id", referencedColumnName = "id")
        private Address address;

        @Enumerated(EnumType.STRING)
        private Role role;

}

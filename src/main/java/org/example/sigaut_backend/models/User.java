package org.example.sigaut_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String paternalName;

    @Column(length = 50, nullable = false)
    private String maternalName;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 150, nullable = false)
    private String password;

    @Column(length = 150)
    private String direction;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Category> categories;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Product> products;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Sale> sales;


    public User(String name, String paternalName, String maternalName, String email, String username, String password, String direction) {
        this.name = name;
        this.paternalName = paternalName;
        this.maternalName = maternalName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.direction = direction;
    }
}

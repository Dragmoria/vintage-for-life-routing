package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.vintageforlife.service.enums.Role;

import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email should be valid")
    @NotNull(message = "Email can not be null")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password can not be blank")
    private String password;

    @Column(name = "active", nullable = false)
    @NotNull(message = "Active can not be null")
    private Boolean active;

    @Column(name = "activation_link")
    @Size(min = 30, max = 30, message = "Activation link should be 30 characters long")
    private String activationLink;

    @Column(name = "role", nullable = false)
    @NotNull(message = "Role can not be blank")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<RouteEntity> routes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(String activationLink) {
        this.activationLink = activationLink;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<RouteEntity> getRoutes() {
        return routes;
    }
}

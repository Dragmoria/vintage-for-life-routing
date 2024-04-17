package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vintageforlife.service.enums.Role;
import com.vintageforlife.service.validation.ValidRole;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    @JsonIgnore
    private Integer id;

    @NotBlank(message = "Name can not be blank")
    private String name;

    @NotNull(message = "Email can not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password can not be blank")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}",
            message = "Password must be at least 8 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special character !@#$%^&+=")
    private String password;

    @NotNull(message = "Active can not be null")
    private Boolean active;

    @JsonIgnore
    private String activationLink;

    @NotNull(message = "Role can not be null")
    @ValidRole(message = "Role is not a valid role")
    private Role role;

    public UserDTO(Integer id, String username, String email, String password, Boolean active, String activationLink, Role role) {
        this.id = id;
        this.name = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.activationLink = activationLink;
        this.role = role;
    }

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

    public Boolean getActive() {
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
}

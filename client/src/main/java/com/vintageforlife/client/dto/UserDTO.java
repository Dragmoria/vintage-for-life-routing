package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vintageforlife.client.enums.Role;
import com.vintageforlife.client.validation.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

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

    @NotNull(message = "Role can not be null")
    @ValidRole(message = "Role is not a valid role")
    private Role role;

    public UserDTO() {
    }

    public UserDTO(Integer id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vintageforlife.service.enums.Role;
import com.vintageforlife.service.validation.ValidRole;
import jakarta.validation.constraints.*;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}

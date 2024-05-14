package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vintageforlife.client.enums.Role;
import com.vintageforlife.client.validation.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
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

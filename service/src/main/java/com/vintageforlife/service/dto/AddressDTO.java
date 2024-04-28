package com.vintageforlife.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vintageforlife.service.validation.SizeThatAllowsNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    @NotBlank(message = "Post code can not be blank")
    @Pattern(regexp = "^[0-9]{4}[A-Z]{2}$", message = "Post code should be in the format 1234AB")
    @NonNull
    private String postCode;

    @NotBlank(message = "Street can not be blank")
    @NonNull
    private String street;

    @NotBlank(message = "House number can not be blank")
    @Positive(message = "House number should be positive")
    @NonNull
    private Integer houseNumber;

    @SizeThatAllowsNull(min = 1, max = 10, message = "Extension should be between 1 and 10 characters")
    private String extension;

    @NotBlank(message = "City can not be blank")
    @NonNull
    private String city;
}

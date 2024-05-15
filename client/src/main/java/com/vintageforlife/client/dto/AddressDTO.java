package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vintageforlife.client.validation.SizeThatAllowsNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO {
    @NotBlank(message = "Post code can not be blank")
    @Pattern(regexp = "^[0-9]{4}[A-Z]{2}$", message = "Post code should be in the format 1234AB")
    private String postCode;

    @NotBlank(message = "Street can not be blank")
    private String street;

    @NotBlank(message = "House number can not be blank")
    @Positive(message = "House number should be positive")
    private Integer houseNumber;

    @SizeThatAllowsNull(min = 1, max = 10, message = "Extension should be between 1 and 10 characters")
    private String extension;

    @NotBlank(message = "City can not be blank")
    private String city;



    public AddressDTO() {
    }
    public AddressDTO(String postCode, String street, Integer houseNumber, String extension, String city) {
        this.postCode = postCode;
        this.street = street;
        this.houseNumber = houseNumber;
        this.extension = extension;
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

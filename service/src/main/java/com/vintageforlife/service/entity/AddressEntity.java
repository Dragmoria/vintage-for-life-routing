package com.vintageforlife.service.entity;

import com.vintageforlife.service.validation.SizeThatAllowsNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "postcode", nullable = false, length = 6)
    @NotBlank(message = "Postcode can not be null")
    private String postcode;

    @Column(name = "street", nullable = false)
    @NotBlank(message = "Street can not be null")
    private String street;

    @Column(name = "house_number", nullable = false)
    @NotBlank(message = "House number can not be null")
    private Integer houseNumber;

    @Column(name = "extension", length = 10)
    @SizeThatAllowsNull(min = 1, max = 10, message = "Extension should be between 1 and 10 characters")
    private String extension;

    @Column(name = "city", nullable = false)
    @NotBlank(message = "City can not be null")
    private String city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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

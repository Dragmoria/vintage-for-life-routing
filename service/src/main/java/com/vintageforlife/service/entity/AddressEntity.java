package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "address", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"postcode", "street", "house_number", "extension", "city"}, name = "UK_ADDRESS")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "postcode", nullable = false, length = 6)
    @NonNull
    private String postcode;

    @Column(name = "street", nullable = false)
    @NonNull
    private String street;

    @Column(name = "house_number", nullable = false)
    @NonNull
    private Integer houseNumber;

    @Column(name = "extension", length = 10)
    private String extension;

    @Column(name = "city", nullable = false)
    @NonNull
    private String city;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY)
    private DistributionCenterEntity distributionCenter;

    @Override
    public String toString() {
        return street + " " + houseNumber + (extension != null ? extension : "") + ", " + postcode + " " + city + ", Netherlands";
    }
}

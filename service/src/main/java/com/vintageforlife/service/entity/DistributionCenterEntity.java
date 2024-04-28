package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "distribution_center")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistributionCenterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "FK_DISTRIBUTION_CENTER_TO_ADDRESS_ID"))
    @NonNull
    private AddressEntity address;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "distributionCenter", fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "distributionCenter", fetch = FetchType.LAZY)
    private List<TransportSettingEntity> transportSettings;
}

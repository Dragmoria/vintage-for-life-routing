package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "transport_setting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "distribution_center_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TRANSPORT_SETTING_DISTRIBUTION_CENTER_ID"))
    @NonNull
    private DistributionCenterEntity distributionCenter;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "value", nullable = false)
    @NonNull
    private String value;
}

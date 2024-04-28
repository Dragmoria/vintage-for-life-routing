package com.vintageforlife.service.entity;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "value", nullable = false)
    @NonNull
    private String value;

    @ManyToOne
    @JoinColumn(name = "distribution_center_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TRANSPORT_SETTING_TO_DISTRIBUTION_CENTER_ID"))
    @NonNull
    private DistributionCenterEntity distributionCenter;
}

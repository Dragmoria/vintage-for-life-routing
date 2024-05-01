package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "transport_setting")
public class TransportSettingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "distribution_center_id", nullable = false, foreignKey = @ForeignKey(name = "FK_DISTRIBUTION_CENTER_ID"))
    private DistributionCenterEntity distributionCenter;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Column(name = "value", nullable = false)
    @NotBlank(message = "Value can not be blank")
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DistributionCenterEntity getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(DistributionCenterEntity distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

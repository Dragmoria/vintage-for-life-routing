package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "product")

public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "distribution_center_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_DISTRIBUTION_CENTER_ID"))
    private DistributionCenterEntity distributionCenter;

    @Column(name = "width", nullable = false)
    @NotBlank(message = "Width can not be blank")
    private Float width;

    @Column(name = "height", nullable = false)
    @NotBlank(message = "Height can not be blank")
    private Float height;

    @Column(name = "depth", nullable = false)
    @NotBlank(message = "Depth can not be blank")
    private Float depth;

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

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getDepth() {
        return depth;
    }

    public void setDepth(Float depth) {
        this.depth = depth;
    }
}
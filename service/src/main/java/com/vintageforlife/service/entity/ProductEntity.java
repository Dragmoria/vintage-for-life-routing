package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "distribution_center_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_DISTRIBUTION_CENTER_ID"))
    @NonNull
    private DistributionCenterEntity distributionCenter;

    @Column(name = "width", nullable = false)
    @NonNull
    private Float width;

    @Column(name = "height", nullable = false)
    @NonNull
    private Float height;

    @Column(name = "depth", nullable = false)
    @NonNull
    private Float depth;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems;
}
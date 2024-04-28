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
    @Getter
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "width")
    private float width;

    @Column(name = "height")
    private float height;

    @Column(name = "depth")
    private float depth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "distribution_center_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCT_TO_DISTRIBUTION_CENTER_ID"))
    @NonNull
    private DistributionCenterEntity distributionCenter;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems;
}

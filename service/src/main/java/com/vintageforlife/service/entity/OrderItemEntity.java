package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_TO_ORDER_ID"))
    @NonNull
    private OrderEntity order;

    @Column(name = "retour", nullable = false)
    @NonNull
    private Boolean retour;

    @Column(name = "completed", nullable = false)
    @NonNull
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_TO_PRODUCT_ID"))
    @NonNull
    private ProductEntity product;

    @OneToOne(mappedBy = "orderItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RouteStepEntity routeStep;
}

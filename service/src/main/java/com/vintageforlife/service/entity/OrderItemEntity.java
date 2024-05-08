package com.vintageforlife.service.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "`order_item`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_ORDER_ID"))
    @NonNull
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_PRODUCT_ID"))
    @NonNull
    private ProductEntity product;

    @Column(name = "retour", nullable = false)
    @NonNull
    private Boolean retour;

    @Column(name = "completed", nullable = false)
    @NonNull
    private Boolean completed;

    @OneToOne(mappedBy = "orderItem", fetch = FetchType.LAZY)
    private RouteStepEntity routeStep;
}
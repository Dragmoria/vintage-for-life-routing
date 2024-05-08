package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "route_step", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"route_id", "step_index"}, name = "UNIQUE_ROUTE_STEP_INDEX")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteStepEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "step_index", nullable = false)
    @NonNull
    private Integer stepIndex;

    @Column(name = "distance_km", nullable = false)
    @NonNull
    private Float distanceKm;

    @Column(name = "completed", nullable = false)
    @NonNull
    private Boolean completed;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ROUTE_STEP_TO_ROUTE_ID"))
    @NonNull
    private RouteEntity route;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_item_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ROUTE_STEP_TO_ORDER_ITEM_ID"))
    @NonNull
    private OrderItemEntity orderItem;
}

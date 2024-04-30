package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "route_step")
public class RouteStepEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "step_index", nullable = false)
    @NotNull(message = "Step index can not be null")
    private Integer stepIndex;

    @Column(name = "distance_km", nullable = false)
    @NotNull(message = "Distance can not be null")
    private Float distanceKm;

    @Column(name = "completed", nullable = false)
    @NotNull(message = "Completed can not be null")
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ROUTE_ID"))
    @NotNull(message = "Route can not be null")
    private RouteEntity route;

    @OneToOne
    @JoinColumn(name = "order_item_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ORDER_ITEM_ID"))
    @NotNull(message = "Order item can not be null")
    private OrderItemEntity orderItem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public Float getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(Float distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    public OrderItemEntity getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemEntity orderItem) {
        this.orderItem = orderItem;
    }
}

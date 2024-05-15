package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteStepDTO {
    @NotNull(message = "Step index can not be null")
    @Positive(message = "Step index should be positive")
    private Integer stepIndex;

    @NotNull(message = "Distance can not be null")
    @Positive(message = "Distance should be positive")
    private Float distanceKm;

    @NotNull(message = "Completed can not be null")
    private Boolean completed;

    @NotNull(message = "Route can not be null")
    private RouteDTO route;

    @NotNull(message = "Order can not be null")
    private OrderDTO order;

    public RouteStepDTO() {
    }

    public RouteStepDTO(Integer stepIndex, Float distanceKm, Boolean completed, RouteDTO route, OrderDTO order) {
        this.stepIndex = stepIndex;
        this.distanceKm = distanceKm;
        this.completed = completed;
        this.route = route;
        this.order = order;
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

    public RouteDTO getRoute() {
        return route;
    }

    public void setRoute(RouteDTO route) {
        this.route = route;
    }

    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}

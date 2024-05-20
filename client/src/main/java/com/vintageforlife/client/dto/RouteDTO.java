package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouteDTO {
    private Integer id;

    @NotNull(message = "Total distance can not be null")
    @Positive(message = "Total distance should be positive")
    private Float totalDistanceKm;

    @NotNull(message = "Completed can not be null")
    private Boolean completed;

    private UserDTO user;

    private List<RouteStepDTO> routeSteps;

    public RouteDTO() {
    }

    public RouteDTO(Integer id, Float totalDistanceKm, Boolean completed, UserDTO user, List<RouteStepDTO> routeSteps) {
        this.id = id;
        this.totalDistanceKm = totalDistanceKm;
        this.completed = completed;
        this.user = user;
        this.routeSteps = routeSteps;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotalDistanceKm() {
        return totalDistanceKm;
    }

    public void setTotalDistanceKm(Float totalDistanceKm) {
        this.totalDistanceKm = totalDistanceKm;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<RouteStepDTO> getRouteSteps() {
        return routeSteps;
    }

    public void setRouteSteps(List<RouteStepDTO> routeSteps) {
        this.routeSteps = routeSteps;
    }
}

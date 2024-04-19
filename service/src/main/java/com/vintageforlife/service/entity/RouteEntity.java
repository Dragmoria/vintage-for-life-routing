package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "route")
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "total_distance_km", nullable = false)
    @NotNull(message = "Total distance can not be null")
    private Float totalDistanceKm;

    @Column(name = "completed", nullable = false)
    @NotNull(message = "Completed can not be null")
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_USER_ID"))
    private UserEntity user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTotalDistanceKm() {
        return totalDistanceKm;
    }

    public void setTotalDistanceKm(Float distanceKm) {
        this.totalDistanceKm = distanceKm;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}

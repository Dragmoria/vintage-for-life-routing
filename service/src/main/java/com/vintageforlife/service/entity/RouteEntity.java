package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "route")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RouteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "total_distance_km", nullable = false)
    @NotNull(message = "Total distance can not be null")
    @NonNull
    private Float totalDistanceKm;

    @Column(name = "completed", nullable = false)
    @NotNull(message = "Completed can not be null")
    @NonNull
    private Boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ROUTE_TO_USER_ID"))
    private UserEntity user;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RouteStepEntity> routeSteps;
}

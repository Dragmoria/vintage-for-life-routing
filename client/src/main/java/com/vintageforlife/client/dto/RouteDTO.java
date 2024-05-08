//package com.vintageforlife.client.dto;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import jakarta.validation.constraints.NotNull;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class RouteDTO {
//    @JsonIgnore
//    private Integer id;
//
//    @NotNull(message = "Total distance can not be null")
//    private Float totalDistanceKm;
//
//    @NotNull(message = "Completed can not be null")
//    private Boolean completed;
//
//    private UserDTO user;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Float getTotalDistanceKm() {
//        return totalDistanceKm;
//    }
//
//    public void setTotalDistanceKm(Float totalDistanceKm) {
//        this.totalDistanceKm = totalDistanceKm;
//    }
//
//    public Boolean getCompleted() {
//        return completed;
//    }
//
//    public void setCompleted(Boolean completed) {
//        this.completed = completed;
//    }
//
//    public UserDTO getUser() {
//        return user;
//    }
//
//    public void setUser(UserDTO user) {
//        this.user = user;
//    }
//
//    public RouteDTO(Integer id, Float totalDistanceKm, Boolean completed, UserDTO user) {
//        this.id = id;
//        this.totalDistanceKm = totalDistanceKm;
//        this.completed = completed;
//        this.user = user;
//    }
//
//    public static class RouteDTOBuilder {
//        private final Integer id;
//        private final Float totalDistanceKm;
//        private Boolean completed;
//        private final UserDTO user;
//
//        public RouteDTOBuilder(Integer id, Float totalDistanceKm, UserDTO user) {
//            this.id = id;
//            this.totalDistanceKm = totalDistanceKm;
//            this.user = user;
//        }
//
//        public RouteDTOBuilder completed(Boolean completed) {
//            this.completed = completed;
//            return this;
//        }
//
//        public RouteDTO build() {
//            return new RouteDTO(id, totalDistanceKm, completed, user);
//        }
//    }
//}

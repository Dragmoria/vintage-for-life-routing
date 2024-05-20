package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SimpleRouteStepDTO {
    @NotNull(message = "Id can not be null")
    @Positive(message = "Id should be positive")
    private Integer id;

    @NotNull(message = "Step index can not be null")
    private Boolean completed;

    public SimpleRouteStepDTO() {
    }

    public SimpleRouteStepDTO(Integer id, Boolean completed) {
        this.id = id;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}

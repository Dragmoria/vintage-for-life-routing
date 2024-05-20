package com.vintageforlife.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {
    @NotBlank(message = "Name can not be blank")
    private String name;

    @Positive(message = "Width should be positive")
    private float width;

    @Positive(message = "Height should be positive")
    private float height;

    @Positive(message = "Depth should be positive")
    private float depth;

    @NotNull(message = "DistributionCenter can not be null")
    private DistributionCenterDTO distributionCenter;

    public ProductDTO() {
    }

    public ProductDTO(String name, float width, float height, float depth, DistributionCenterDTO distributionCenter) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.distributionCenter = distributionCenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public DistributionCenterDTO getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(DistributionCenterDTO distributionCenter) {
        this.distributionCenter = distributionCenter;
    }
}

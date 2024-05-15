package com.vintageforlife.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestRectangle {
    private Integer width;

    private Integer length;

    private Integer x;

    private Integer y;

    public TestRectangle(Integer width, Integer length) {
        this.width = width;
        this.length = length;
    }

    public TestRectangle(Integer width, Integer length, Integer x, Integer y) {
        this.width = width;
        this.length = length;
        this.x = x;
        this.y = y;
    }

    public boolean canFit(TestRectangle rectangle) {
        return width >= rectangle.getWidth() && length >= rectangle.getLength();
    }

    public Integer getHighestPoint() {
        return y + length;
    }
}

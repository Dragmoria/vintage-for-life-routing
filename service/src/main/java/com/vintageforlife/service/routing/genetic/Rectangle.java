package com.vintageforlife.service.routing.genetic;

public class Rectangle {
    private final Float width;
    private final Float length;

    public Rectangle(Float width, Float length) {
        this.width = width;
        this.length = length;
    }

    public Float getWidth() {
        return width;
    }

    public Float getLength() {
        return length;
    }

    public Float getArea() {
        return width * length;
    }

    public boolean canFit(Rectangle rectangle) {
        return width >= rectangle.getWidth() && length >= rectangle.getLength();
    }
}

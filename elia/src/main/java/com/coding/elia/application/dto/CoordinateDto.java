package com.coding.elia.application.dto;

import com.coding.elia.domain.model.Coordinate;

public record CoordinateDto(int x, int y) {

    public static CoordinateDto from(Coordinate coordinate) {
        return new CoordinateDto(coordinate.getX(), coordinate.getY());
    }

    public Coordinate to() {
        var coordinate = new Coordinate();
        coordinate.setX(x);
        coordinate.setY(y);
        return coordinate;
    }
}

package com.coding.elia.application.dto;

import com.coding.elia.domain.model.Shape;

import java.util.UUID;

public record ShapeDto(UUID id, CoordinateDto coordinate) {

    public static ShapeDto from(Shape shape) {
        return new ShapeDto(shape.getUuid(), CoordinateDto.from(shape.getCoordinate()));
    }
}

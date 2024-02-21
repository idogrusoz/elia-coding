package com.coding.elia.infrastructure;

import com.coding.elia.application.shape.ShapeService;
import com.coding.elia.application.dto.CoordinateDto;
import com.coding.elia.application.dto.ShapeDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/shapes")
public class ShapeController {

    private final ShapeService shapeService;

    public ShapeController(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    @PostMapping
    public ShapeDto createShape(@RequestBody CoordinateDto coordinate) {
        return shapeService.createShape(coordinate);
    }

    @PutMapping("/{shapeId}/move")
    public ShapeDto moveShape(@RequestBody CoordinateDto coordinate, @PathVariable("shapeId") String shapeId) {
        return shapeService.moveShape(coordinate, shapeId);
    }

}

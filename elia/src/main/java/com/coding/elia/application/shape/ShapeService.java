package com.coding.elia.application.shape;

import com.coding.elia.application.coordinate.CoordinateRepository;
import com.coding.elia.application.dto.CoordinateDto;
import com.coding.elia.application.dto.ShapeDto;
import com.coding.elia.application.exceptions.ApplicationFailureException;
import com.coding.elia.domain.model.Shape;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.UUID;

@Service
@JBossLog
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final CoordinateRepository coordinateRepository;

    public ShapeService(ShapeRepository shapeRepository, CoordinateRepository coordinateRepository) {
        this.shapeRepository = shapeRepository;
        this.coordinateRepository = coordinateRepository;
    }

    @Transactional
    public ShapeDto createShape(CoordinateDto coordinateDto) {
        try {
            var coordinate = coordinateDto.to();
            var shape = new Shape();
            shape.setCoordinate(coordinate);
            shape.setUuid(UUID.randomUUID());
            coordinate.setShape(shape);
            coordinateRepository.save(coordinate);
            shapeRepository.saveAndFlush(shape);
            return ShapeDto.from(shape);
        } catch (IllegalArgumentException | OptimisticLockingFailureException exception) {
            log.errorf("Error while creating a new shape", exception);
            throw new ApplicationFailureException("Error while creating a new shape. Please try again");
        }
    }
}

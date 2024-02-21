package com.coding.elia.application.shape;

import com.coding.elia.application.coordinate.CoordinateRepository;
import com.coding.elia.application.dto.CoordinateDto;
import com.coding.elia.application.dto.ShapeDto;
import com.coding.elia.application.exceptions.ApplicationFailureException;
import com.coding.elia.application.exceptions.NotFoundException;
import com.coding.elia.domain.model.Shape;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

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

    @Transactional
    public ShapeDto moveShape(CoordinateDto coordinateDto, String shapeId) {
        try {
            var coordinate = coordinateDto.to();
            var shape = shapeRepository.findByUuid(UUID.fromString(shapeId)).orElseThrow(EntityNotFoundException::new);
            coordinate.setShape(shape);
            coordinateRepository.save(coordinate);
            shape.moveTo(coordinate);
            shapeRepository.saveAndFlush(shape);
            return ShapeDto.from(shape);
        } catch (EntityNotFoundException e){
            log.errorf("Shape with id %s is not found", shapeId);
            throw new NotFoundException(String.format("Shape with id %s is not found", shapeId));
        } catch ( OptimisticLockingFailureException exception) {
            log.errorf("Error while creating a new shape", exception);
            throw new ApplicationFailureException("Error while creating a new shape. Please try again");
        }
    }
}

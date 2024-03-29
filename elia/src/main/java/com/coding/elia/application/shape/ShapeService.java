package com.coding.elia.application.shape;

import com.coding.elia.application.context.UserEmailContext;
import com.coding.elia.application.coordinate.CoordinateRepository;
import com.coding.elia.application.dto.CoordinateDto;
import com.coding.elia.application.dto.ShapeDto;
import com.coding.elia.application.exceptions.ApplicationFailureException;
import com.coding.elia.application.exceptions.UnauthorizedException;
import com.coding.elia.application.exceptions.UnmappableIdException;
import com.coding.elia.application.exceptions.NotFoundException;
import com.coding.elia.domain.model.Shape;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.jbosslog.JBossLog;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@JBossLog
public class ShapeService {

    private final ShapeRepository shapeRepository;
    private final CoordinateRepository coordinateRepository;

    private static final String SHAPE_NOT_FOUND = "Shape with for %s is not found";
    private static final String WRONG_SHAPE_ID = "Received a wrong type of shape id";

    public ShapeService(ShapeRepository shapeRepository, CoordinateRepository coordinateRepository) {
        this.shapeRepository = shapeRepository;
        this.coordinateRepository = coordinateRepository;
    }

    @Transactional
    public ShapeDto createShape(CoordinateDto coordinateDto) {
        var userEmail = UserEmailContext.getUserContext();
        try {
            var coordinate = coordinateDto.to();
            var shape = new Shape();
            shape.setCoordinates(List.of(coordinate));
            shape.setUuid(UUID.randomUUID());
            shape.setUserEmail(userEmail);
            coordinate.setShape(shape);
            coordinateRepository.save(coordinate);
            shapeRepository.saveAndFlush(shape);
            return ShapeDto.from(shape);
        } catch (UnmappableIdException | OptimisticLockingFailureException exception) {
            log.errorf("Error while creating a new shape", exception);
            throw new ApplicationFailureException("Error while creating a new shape. Please try again");
        }
    }

    @Transactional
    public ShapeDto moveShape(CoordinateDto coordinateDto, String shapeId) {
        try {
            var coordinate = coordinateDto.to();
            var shape = shapeRepository.findByUuid(UUID.fromString(shapeId)).orElseThrow(EntityNotFoundException::new);
            validateAuthority(shape);
            coordinate.setShape(shape);
            coordinateRepository.save(coordinate);
            shape.moveTo(coordinate);
            shapeRepository.saveAndFlush(shape);
            return ShapeDto.from(shape);
        } catch (EntityNotFoundException e) {
            log.errorf(SHAPE_NOT_FOUND, shapeId);
            throw new NotFoundException(String.format(SHAPE_NOT_FOUND, shapeId));
        } catch (OptimisticLockingFailureException e) {
            log.errorf("Error while creating a new shape", e);
            throw new ApplicationFailureException("Error while creating a new shape. Please try again");
        } catch (IllegalArgumentException e) {
            log.errorf(WRONG_SHAPE_ID, e);
            throw new UnmappableIdException(WRONG_SHAPE_ID);
        }
    }

    private void validateAuthority(Shape shape) {
        var userEmail = UserEmailContext.getUserContext();
        if(!shape.getUserEmail().equals(userEmail)) {
            throw new UnauthorizedException();
        }
    }

    public ShapeDto getShape(String shapeId) {
        try {
            var optionalShape = shapeRepository.findByUuid(UUID.fromString(shapeId));
            if(optionalShape.isPresent()) {
                validateAuthority(optionalShape.get());
                return ShapeDto.from(optionalShape.get());
            } else {
                throw new EntityNotFoundException();
            }
        } catch (EntityNotFoundException e) {
            log.errorf(SHAPE_NOT_FOUND, shapeId);
            throw new NotFoundException(String.format(SHAPE_NOT_FOUND, shapeId));
        } catch (IllegalArgumentException e) {
            log.errorf(WRONG_SHAPE_ID, e);
            throw new UnmappableIdException(WRONG_SHAPE_ID);
        }
    }

    public ShapeDto getShapeByUserEmail() {
        var userEmail = UserEmailContext.getUserContext();
        try {
            return shapeRepository.findByUserEmail(userEmail).map(ShapeDto::from).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            log.errorf(SHAPE_NOT_FOUND, userEmail);
            throw new NotFoundException(String.format(SHAPE_NOT_FOUND, userEmail));
        }
    }
}

package com.coding.elia.application.shape;

import com.coding.elia.domain.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ShapeRepository extends JpaRepository<Shape, Long> {

    Optional<Shape> findByUuid(UUID uuid);
    Optional<Shape> findByUserEmail(String email);
}

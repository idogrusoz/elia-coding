package com.coding.elia.application.shape;

import com.coding.elia.domain.model.Shape;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShapeRepository extends JpaRepository<Shape, Long> {
}

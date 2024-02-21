package com.coding.elia.application.coordinate;

import com.coding.elia.domain.model.Coordinate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
}

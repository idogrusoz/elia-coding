package com.coding.elia.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shapes")
@NoArgsConstructor
@AllArgsConstructor
public class Shape {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(unique = true)
    private UUID uuid;

    @Getter
    @Setter
    @OneToMany(mappedBy = "shape", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Coordinate> coordinates;

    public void moveTo(Coordinate coordinate) {
        var coordinateList = getCoordinates();
        coordinateList.add(coordinate);
        setCoordinates(coordinateList);
    }

    public Coordinate getCoordinate() {
        if (coordinates.size() == 1) {
            return coordinates.getFirst();
        } else {
            return coordinates.stream().max(Comparator.comparing(Coordinate::getCreationDateTime)).orElseThrow();
        }
    }
}

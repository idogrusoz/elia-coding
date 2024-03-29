package com.coding.elia.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "coordinates")
@NoArgsConstructor
@AllArgsConstructor
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    private int x;

    @Getter
    @Setter
    private int y;

    @Getter
    @CreationTimestamp
    OffsetDateTime creationDateTime;

    @ManyToOne
    @Setter
    @JoinColumn(referencedColumnName = "uuid")
    private Shape shape;
}

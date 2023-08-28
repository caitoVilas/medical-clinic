package com.medicalclinic.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Entity
@Table(name = "specialities")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SpecialityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "specialities")
    private List<DoctorEntity> doctors;
}

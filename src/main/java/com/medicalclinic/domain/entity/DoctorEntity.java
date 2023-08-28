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
@Table(name = "doctors")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DoctorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String licence;
    private String dni;
    private String tel;
    private String email;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "doctor_speciality", joinColumns = @JoinColumn(name = "doctor_id"),
    inverseJoinColumns = @JoinColumn(name = "speciality_id"))
    private List<SpecialityEntity> specialities;
}

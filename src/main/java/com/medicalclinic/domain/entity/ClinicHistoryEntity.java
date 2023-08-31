package com.medicalclinic.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Entity
@Table(name = "clinical_histories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClinicHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @OneToMany(mappedBy = "clinicalHistory")
    private List<HistoryDetailEntity> details;

}

package com.medicalclinic.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Entity
@Table(name = "historial_details")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HistoryDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String history;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clinical_history_id")
    private ClinicHistoryEntity clinicalHistory;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;


}

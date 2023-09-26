package com.medicalclinic.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Entity
@Table(name = "doctor_schedule")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DoctorScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WorkdayEntity> workDays;
}

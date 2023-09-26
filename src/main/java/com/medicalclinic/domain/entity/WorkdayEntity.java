package com.medicalclinic.domain.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Entity
@Table(name = "work_days")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class WorkdayEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
}

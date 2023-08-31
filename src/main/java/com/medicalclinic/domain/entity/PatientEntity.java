package com.medicalclinic.domain.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author claudio.vilas
 * date 08/2023
 */

@Entity
@Table(name = "patients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String dni;
    private String tel;
    private String email;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "clincal_id")
    private ClinicalEntity clinical;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "socialWork_id")
    private SocialWorkEntity socialWork;
    private String SocialWorkMembership;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "clinicalHistory_id")
    private ClinicHistoryEntity clinicalHistory;

}

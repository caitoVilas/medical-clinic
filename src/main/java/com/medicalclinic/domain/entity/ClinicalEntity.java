package com.medicalclinic.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author claudio.vilas
 * date:08/2023
 */

@Entity
@Table(name = "clinicals")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClinicalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    private String tel;
    private String email;
}

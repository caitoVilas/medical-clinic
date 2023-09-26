package com.medicalclinic.util.map;

import com.medicalclinic.api.models.response.DoctorResponse;
import com.medicalclinic.api.models.response.DoctorScheduleRespone;
import com.medicalclinic.api.models.response.SpecialityResponse;
import com.medicalclinic.api.models.response.WorkDayResponse;
import com.medicalclinic.domain.entity.DoctorEntity;
import com.medicalclinic.domain.entity.DoctorScheduleEntity;
import com.medicalclinic.domain.entity.SpecialityEntity;
import com.medicalclinic.domain.entity.WorkdayEntity;

import java.util.stream.Collectors;

/**
 * @author claudio.vilas
 * date 08/2023
 */

public class MapUtil {

    public static DoctorScheduleRespone mapDoctorScheduleToDto(DoctorScheduleEntity schedule){
        return DoctorScheduleRespone.builder()
                .id(schedule.getId())
                .doctor(mapToDoctorDto(schedule.getDoctor()))
                .workdays(schedule.getWorkDays()
                        .stream().map(MapUtil::mapToWorkdayDto).collect(Collectors.toList()))
                .build();
    }

    public static DoctorResponse mapToDoctorDto(DoctorEntity doctor){
        return DoctorResponse.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .licence(doctor.getLicence())
                .dni(doctor.getDni())
                .tel(doctor.getTel())
                .email(doctor.getEmail())
                .especialities(doctor.getSpecialities()
                        .stream().map(MapUtil::mapSpecialityToDto).collect(Collectors.toList()))
                .build();
    }

    public static SpecialityResponse mapSpecialityToDto(SpecialityEntity speciality){
        return SpecialityResponse.builder()
                .id(speciality.getId())
                .name(speciality.getName())
                .build();
    }

    public static WorkDayResponse mapToWorkdayDto(WorkdayEntity workday){
        return WorkDayResponse.builder()
                .id(workday.getId())
                .dayOfWeek(workday.getDayOfWeek())
                .startTime(workday.getStartTime())
                .endTime(workday.getEndTime())
                .build();
    }
}

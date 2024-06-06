package org.javaacademy.mapstruct_homework.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PersonInsuranceDto {
    private String fullName; //ФИО
    private String fullAddress; //Полный адрес проживания человека
    private Integer fullAge; //Количество исполнившихся лет (на сегодняшний день)
}

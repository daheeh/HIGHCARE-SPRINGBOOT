package com.highright.highcare.pm.dto;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryDTO {

    private Integer milNo;
    private int empNo;
    private String status;
    private char isWhether;

}

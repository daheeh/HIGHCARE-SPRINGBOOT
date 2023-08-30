package com.highright.highcare.pm.dto;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {

    private int jobCode;
    private String name;
    private Integer upperJobCode;
    private String upperJobName;
}

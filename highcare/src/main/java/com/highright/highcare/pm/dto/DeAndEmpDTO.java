package com.highright.highcare.pm.dto;


import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeAndEmpDTO {

    private int id;
    private String text;
    private String tel;
    private Integer parent;
    private boolean droppable;
    private String name;
    private String jobName;

}

package com.highright.highcare.approval.dto;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvMeetingLogDTO {


    private String apvNo;
    private Date meetingDate;
    private String location;
    private char participants;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ApvVacationDTO {

        private String apvNo;
        private Date workingDate;
        private Time startTime;
        private Time endTime;
        private String reason;

    }
}

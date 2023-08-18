package com.highright.highcare.approval.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_MEETINGLOG")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvMeetingLogDTO {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "MEETING_DATE")
    private Date meetingDate;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PARTICIPANTS")
    private char participants;

}

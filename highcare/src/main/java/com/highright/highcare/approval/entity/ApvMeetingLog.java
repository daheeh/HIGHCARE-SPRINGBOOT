package com.highright.highcare.approval.entity;

import com.highright.highcare.approval.dto.ApvFormDTO;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "TBL_APV_MEETINGLOG")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SequenceGenerator(
        name = "SEQ_APV_ITEMS",
        sequenceName = "SEQ_APV_ML_ITEMS",
        initialValue = 1, allocationSize = 1
)
public class ApvMeetingLog {

    @Id
    @Column(name = "ITEMS_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_APV_ITEMS"
    )
    private Long itemsNo;

    @Column(name = "MEETING_TITLE")
    private String meetingTitle;

    @Column(name = "MEETING_DATE")
    private Date meetingDate;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PARTICIPANTS")
    private String participants;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private ApvForm apvForm;
}

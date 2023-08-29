package com.highright.highcare.approval.dto;
import lombok.*;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvFormDTO {

    private Long apvNo;
    private String title;
    private Date writeDate;
    private String apvStatus;
    private String isUrgency;
    private String category;
    private String contents1;
    private String contents2;
    private int empNo;

    /* 업무 */
    private List<ApvMeetingLogDTO> apvMeetingLogs;
    private List<ApvBusinessTripDTO> apvBusinessTrips;

    /* 지출 */
    private List<ApvExpFormDTO> apvExpForms;
    private List<ApvFamilyEventDTO> apvFamilyEvents;
    private List<ApvCorpCardDTO> apvCorpCards;

    /* 인사 */
    private List<ApvVacationDTO> apvVacations;
    private List<ApvIssuanceDTO> apvIssuances;

    /* 결재라인*/
    private List<ApvLineDTO> apvLines;

}

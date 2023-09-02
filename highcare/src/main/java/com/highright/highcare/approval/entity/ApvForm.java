package com.highright.highcare.approval.entity;

import com.highright.highcare.pm.entity.PmEmployee;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_APV_FORM")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SequenceGenerator(
        name = "APV_SEQ_NO",
        sequenceName = "SEQ_APV_NO",
        initialValue = 1, allocationSize = 1
)
public class ApvForm {

    @Id
    @Column(name = "APV_NO")
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "APV_SEQ_NO"
    )
    private Long apvNo;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "WRITE_DATE")
    private Date writeDate;

    @Column(name = "APV_STATUS")
    private String apvStatus;

    @Column(name = "ISURGENCY")
    private String isUrgency;

    @Column(name = "APV_CATEGORY")
    private String category;

    @Column(name = "CONTENTS1")
    private String contents1;

    @Column(name = "CONTENTS2")
    private String contents2;

    @Column(name = "EMP_NO")
    private int empNo;

    @ManyToOne
    @JoinColumn(name = "EMP_NO", updatable = false, insertable = false)
    private ApvEmployee apvEmployee;

    //    @Where(clause = "category = '업무'")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvMeetingLog> apvMeetingLogs = new ArrayList<>();

    //    @Where(clause = "category = '업무'")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvBusinessTrip> apvBusinessTrips = new ArrayList<>();

    //    @Where(clause = "category = '지출'")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvExpForm> apvExpForms = new ArrayList<>();

    //    @Where(clause = "category = '지출'")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvFamilyEvent> apvFamilyEvents = new ArrayList<>();

    //    @Where(clause = "category = '지출'")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvCorpCard> apvCorpCards = new ArrayList<>();


    //    @Where(clause = "category = '인사'")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvVacation> apvVacations = new ArrayList<>();

    //    @Where(clause = "category = '인사'")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvIssuance> apvIssuances = new ArrayList<>();

    // 결재라인
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "APV_NO")
    private List<ApvLine> apvLines = new ArrayList<>();


    @Override
    public String toString() {
        return "ApvForm{" +
                "apvNo=" + apvNo +
                ", title='" + title +
                ", writeDate=" + writeDate +
                ", apvStatus='" + apvStatus +
                ", isUrgency='" + isUrgency +
                ", category='" + category +
                ", contents1='" + contents1 +
                ", contents2='" + contents2 +
                ", empNo=" + empNo +
                ", apvEmployee=" + apvEmployee + '\'' +
                ", apvMeetingLogs=" + apvMeetingLogs + '\'' +
                ", apvBusinessTrips=" + apvBusinessTrips + '\'' +
                ", apvExpForms=" + apvExpForms + '\'' +
                ", apvFamilyEvents=" + apvFamilyEvents + '\'' +
                ", apvCorpCards=" + apvCorpCards + '\'' +
                ", apvVacations=" + apvVacations + '\'' +
                ", apvIssuances=" + apvIssuances + '\'' +
                ", apvLines=" + apvLines + '\'' +
                '}';
    }
}

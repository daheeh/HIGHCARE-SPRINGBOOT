package com.highright.highcare.reservation.entity;

import com.highright.highcare.bulletin.entity.BulletinEmployee;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "TBL_RESOURCE_RESERVATION_STATUS")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResourceReservationStatus {
    @Id
    @Column(name = "STATUS_CODE")
    private int statusCode;
    @Column(name = "RESERVATION_STATUS")
    private String reservationStatus;
    @Column(name = "START_TIME")
    private String startTime;
    @Column(name = "END_TIME")
    private String endTime;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "RESERVATION_DATE")
    private java.sql.Date reservationDate;
    @ManyToOne
    @JoinColumn(name = "EMP_NO")
    private BulletinEmployee bulletinEmployee;
    @ManyToOne
    @JoinColumn(name = "RESOURCE_CODE")
    private Resource resource;

}

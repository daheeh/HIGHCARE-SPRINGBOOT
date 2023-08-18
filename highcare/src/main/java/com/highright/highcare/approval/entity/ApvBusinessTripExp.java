package com.highright.highcare.approval.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_APV_BUSINESS_TRIP_EXP")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApvBusinessTripExp {

    @Id
    @Column(name = "APV_NO")
    private String apvNo;

    @Column(name = "BT_PERIOD")
    private String period;

    @Column(name = "BT_LOCATION")
    private Number location;

    @Column(name = "BT_NUMBER")
    private Number personNum;

    @Column(name = "REF_APV_NO")
    private String refApvNo;
}

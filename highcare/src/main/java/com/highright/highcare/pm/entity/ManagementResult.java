package com.highright.highcare.pm.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ManagementResult {

    Long datetime = System.currentTimeMillis();
    Timestamp timestamp = new Timestamp(datetime);

//        System.out.println("Datetime = " + datetime);
//        System.out.println("Timestamp: "+timestamp);
}

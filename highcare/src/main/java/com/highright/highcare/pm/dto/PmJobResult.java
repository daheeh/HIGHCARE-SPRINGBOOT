//package com.highright.highcare.pm.dto;
//
//import com.highright.highcare.pm.entity.PmJob;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Getter
//@AllArgsConstructor
//@NoArgsConstructor
//public class PmJobResult {
//
//    private Integer jobCode;
//    private String name;
//    private Integer upperJobCode;
//    private String upperJobName;
//    private List<PmJobResult> children;
//
//    public static PmJobResult of(PmJob pmJob){
//        return new PmJobResult(
//                pmJob.getJobCode(),
//                pmJob.getName(),
//                pmJob.getUpperJobCode(),
//                pmJob.getUpperJobName(),
//                pmJob.getChildren().stream().map(PmJobResult::of).collect(Collectors.toList())
//        );
//    }
//}

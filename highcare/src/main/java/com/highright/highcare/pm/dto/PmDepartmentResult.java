package com.highright.highcare.pm.dto;


import com.highright.highcare.pm.entity.PmDepartment;
import com.highright.highcare.pm.entity.PmEmployee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PmDepartmentResult {
    private Integer deptCode; // 여기도 같이 변경해줘야함
    private String name;
    private String tel;
    private String upperName;
    private Integer upperCode;
    private List<PmDepartmentResult> children;
    private PmEmployee employee;

    public static PmDepartmentResult of(PmDepartment pmDepartment){
        return new PmDepartmentResult(
                pmDepartment.getDeptCode(),
                pmDepartment.getName(),
                pmDepartment.getTel(),
                pmDepartment.getUpperName(),
                pmDepartment.getUpperCode(),
                pmDepartment.getChildren().stream().map(PmDepartmentResult::of).collect(Collectors.toList()),
                pmDepartment.getEmployee()
        );

    }

}

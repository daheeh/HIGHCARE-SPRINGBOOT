package com.highright.highcare.pm.entity;


import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PmDepartmentResult {
    private int id;
    private String name;
    private String title;
    private String phone;
    private String email;
    //    private String deName;
    private List<PmDepartmentResult> children;
//    private List<PmEmployeeResult> employees;

    public PmDepartmentResult(PmDepartment departments) {

        this.id = departments.getDeptCode();
        this.name = departments.getName();
        this.title = departments.getUpperName();


        // 부서의 하위 부서를 DepartmentResult 객체로 매핑
        this.children = departments.getChildren().stream().map(PmDepartmentResult::new).collect(Collectors.toList());

        // 부서의 사원을 children 리스트에 추가
        for (PmEmployeeResult employee :  departments.getEmployees().stream().map(PmEmployeeResult::new).collect(Collectors.toList())) {
            if(!employee.getName().equals("0")){
                System.out.println(employee.getName());
                this.children.add(new PmDepartmentResult(employee));
            }

        }

    }

    public PmDepartmentResult(PmEmployeeResult employee) {
        this.title = employee.getName();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.id = employee.getEmpNo();
        this.name = employee.getTitle();
        //부서 코드에 해당하는 정보 넣기 (예: 사원의 부서 코드)
        // 다른 필드들을 적절히 초기화

    }


}

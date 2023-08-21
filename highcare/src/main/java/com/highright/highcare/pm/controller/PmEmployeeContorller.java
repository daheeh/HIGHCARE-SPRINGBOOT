package com.highright.highcare.pm.controller;

import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.pm.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pm")
@Slf4j
public class PmEmployeeContorller {

    private final EmployeeService employeeService;

    public PmEmployeeContorller(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public ResponseEntity<ResponseDTO> selectEmployeeList(
            @RequestParam(name= "offset", defaultValue = "1") String offset){

//        int total = employeeService.selectEmployeeList();

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK, "조회 성공", employeeService.selectEmployeeList(offset)));
    }


//    @RequestMapping("/pm")
//    public String pmMain(){
//        log.info("pm main");
//        return "pmMain";
//    }
}

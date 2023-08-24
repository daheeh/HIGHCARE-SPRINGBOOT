package com.highright.highcare.pm.controller;

import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pm")
@Slf4j
public class PmEmployeeContorller {

    private final EmployeeService employeeService;

    public PmEmployeeContorller(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> selectEmployeeAllList(
            @RequestParam(name = "offset", defaultValue = "1") String offset){
        log.info("start========================================================");
        log.info("offset=============================== : {}", offset);

        int total = employeeService.selectEmployeeTotal();

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(employeeService.selectEmployeeAllList(cri));
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",  pagingResponseDTO));
    }

    //쿼리메소드에있는 내용만 바꾸면 된다..
    // 사원 상세조회
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> selectEmployeeList(
            @RequestParam(name = "offset",defaultValue = "1", required = false) String offset, @RequestBody String empName){
        log.info("offset===================> {}", offset);
        log.info("empName==============> {}", empName);

        int total = employeeService.selectEmployeeTotal(empName); // 조건을 추가
        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
        cri.setSearchValue(empName);

        List<PmEmployeeDTO> employeeList = employeeService.selectEmployeeSearchList(cri, empName);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(employeeList);
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",pagingResponseDTO));

    }

    /* 사원 등록 */
    @PostMapping("/all")
    public ResponseEntity<ResponseDTO> insertPmEmployee(@RequestBody PmEmployeeDTO pmEmployeeDTO){
        log.info("inserPmEmployee=========================>", pmEmployeeDTO);
        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),"사원 등록 성공",
                        employeeService.insertPmEmployee(pmEmployeeDTO)));
    }

//    @GetMapping("/department")
//    public ResponseEntity<?> getPmDepartmentList() {
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),"부서 조회 성공", employeeService.getPmDepartmentList()));
//    }

    @GetMapping("selectDept")
    public ResponseEntity<ResponseDTO> selectDept() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),"부서조회성공", employeeService.selectDept()));
    }

    /* 사원 수정 */
    @PutMapping(value = "search")
    public ResponseEntity<ResponseDTO> updateEmployee(@ModelAttribute PmEmployeeDTO pmEmployeeDTO){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "사원 수정 성공", employeeService.updateEmployee(pmEmployeeDTO)));
    }

    @GetMapping("secondDept")
    public ResponseEntity<ResponseDTO> secondDept() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),"부서조회성공", employeeService.secondDept()));
    }




//    @GetMapping("/empde")
//    public ResponseEntity<ResponseDTO> selectEmployeeWithDepartment(@RequestParam(name="offset", defaultValue="1") String offset){
//        log.info("selectEmployeeDpartment ================ : " + offset);
//        int total = employeeService.selectEmployeeTotal();
//
//        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
//        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//        pagingResponseDTO.setData(employeeService.selectEmployeeWithDepartment(cri));
//        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",pagingResponseDTO));
//
//
//    }


}

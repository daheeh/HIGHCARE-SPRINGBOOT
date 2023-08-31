package com.highright.highcare.pm.controller;

import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.pm.dto.DepartmentDTO;
import com.highright.highcare.pm.dto.ManagementDTO;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.MgEmployee;
import com.highright.highcare.pm.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/pm")
@Slf4j
public class PmEmployeeContorller {

    private final EmployeeService employeeService;

    public PmEmployeeContorller(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /* 사원 전체 조회 */
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

    /* 사원 검색 */
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


    /* 트리뷰 */
    @GetMapping("selectDept")
    public ResponseEntity<ResponseDTO> selectDept() {

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),"부서조회성공", employeeService.selectDept()));
    }

    /* 사원 수정 */
    @PutMapping(value = "search")
    public ResponseEntity<ResponseDTO> updateEmployee(@ModelAttribute PmEmployeeDTO pmEmployeeDTO){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "사원 수정 성공", employeeService.updateEmployee(pmEmployeeDTO)));
    }

    /* 라인 트리뷰 */
    @GetMapping("secondDept")
    public ResponseEntity<ResponseDTO> secondDept() {
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),"사원조회성공", employeeService.secondDept()));
    }

    /* 출/퇴근 조회 */
    @GetMapping("management")
    public ResponseEntity<ResponseDTO> manageMent(@RequestParam(name = "offset", defaultValue = "1") String offset) {

        log.info("start============================================");
        log.info("offset=============================== : {}", offset);

        int total = employeeService.selectEmployeeTotal();

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(employeeService.manageMent(cri));
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));


        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",  employeeService.manageMent(cri)));

//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "근태 조회 성공", employeeService.manageMent()));
    }

    /* 출근 */
    @PostMapping("management/insert")
    public ResponseEntity<ResponseDTO> insertmanageMent(@RequestBody ManagementDTO managementDTO){
        log.info("insertmanageMent=========================>", managementDTO);
        // 데이터베이스에 넣을 날짜형식을 갈라서..만들어요

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate currentDate = currentDateTime.toLocalDate();
        LocalTime currentTime = currentDateTime.toLocalTime();

        String yearMonthDay = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        managementDTO.setStartTime(formattedTime);
        managementDTO.setEndTime(null); // 퇴근 시간은 초기값으로 설정/
        managementDTO.setManDate(yearMonthDay); // 년월일만 설정

//        LocalDateTime currentDateTime = LocalDateTime.now();
//        String yearMonthDay = currentDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//        managementDTO.setStartTime(Timestamp.valueOf(currentDateTime)); // 시간 분 초 설정
//        managementDTO.setEndTime(null);
//        managementDTO.setManDate(yearMonthDay);


//        LocalDateTime currentDateTime = LocalDateTime.now();
//        String yearMonthDay = currentDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        String formattedTime = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//
//        managementDTO.setStartTime(Timestamp.valueOf(currentDateTime));
//        managementDTO.setEndTime(null); // 퇴근 시간은 초기값으로 설정
//        managementDTO.setManDate(formattedTime);
//////////////////////////////////////////////////////////////////////////
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        LocalTime currentTime = currentDateTime.toLocalTime();
//
//        // managementDTO 객체에 startTime과 endTime 설정
//        managementDTO.setStartTime(Timestamp.valueOf(currentDateTime));
//        managementDTO.setEndTime(null);
//        managementDTO.setManDate(String.valueOf(currentDateTime));

        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),"출근 등록",
                        employeeService.insertmanageMent(managementDTO)));
    }

//    @PostMapping("/all")
//    public ResponseEntity<ResponseDTO> insertPmEmployee(@RequestBody PmEmployeeDTO pmEmployeeDTO){
//        log.info("inserPmEmployee=========================>", pmEmployeeDTO);
//        return ResponseEntity.ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(),"사원 등록 성공",
//                        employeeService.insertPmEmployee(pmEmployeeDTO)));
//    }


}

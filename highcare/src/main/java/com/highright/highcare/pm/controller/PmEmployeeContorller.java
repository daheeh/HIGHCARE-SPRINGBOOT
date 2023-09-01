package com.highright.highcare.pm.controller;

import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.pm.dto.DepartmentDTO;
import com.highright.highcare.pm.dto.ManagementDTO;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.ManagementResult;
import com.highright.highcare.pm.entity.MgEmployee;
import com.highright.highcare.pm.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.modifier.Mandate;
import org.springframework.format.annotation.DateTimeFormat;
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


        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),"출근 등록",
                        employeeService.insertmanageMent(managementDTO)));
    }


//    @GetMapping("management/search")
//    public ResponseEntity<ResponseDTO> updatesearchManageMent(
//            @RequestParam(name = "offset",defaultValue = "1", required = false) String offset, @RequestBody int empNo){
//        log.info("offset===================> {}", offset);
//        log.info("empNo==============> {}", empNo);
//
//        int total = employeeService.selectEmployeeTotal(String.valueOf(empNo)); // 조건을 추가
//        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
//        cri.setSearchValue(String.valueOf(empNo));
//
//        List<PmEmployeeDTO> mgEmployeeList = employeeService.manageMentsearch(cri, empNo);
//
//        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//        pagingResponseDTO.setData(mgEmployeeList);
//        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));
//
//        return ResponseEntity
//                .ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",pagingResponseDTO));
//
//    }

    /* 퇴근 조회 */
    @GetMapping("management/search")
    public ResponseEntity<ResponseDTO> updatesearchManageMent(
            @RequestParam(name = "offset", defaultValue = "1", required = false) String offset,
            @RequestParam(name = "empNo") int empNo
    ) { // Use @RequestParam instead of @RequestBody
        log.info("offset===================> {}", offset);
        log.info("empNo==============> {}", empNo);

        int total = employeeService.selectEmployeeTotal(String.valueOf(empNo)); // 조건을 추가
        Criteria cri = new Criteria(Integer.valueOf(offset), 10);
        cri.setSearchValue(String.valueOf(empNo));

        List<ManagementResult> mgEmployeeList = employeeService.manageMentsearch(cri, empNo);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(mgEmployeeList);
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공", pagingResponseDTO));
    }


    /* 퇴근 */
    @PostMapping("management/update")
    public ResponseEntity<ResponseDTO> updateManageMent(@RequestBody ManagementDTO managementDTO){
        log.info("updatemanageMent=========================>", managementDTO);
        // 데이터베이스에 넣을 날짜형식을 갈라서..만들어요

        /* 퇴근 시간 등록 */
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();

        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        managementDTO.setEndTime(formattedTime); // 퇴근 시간은 초기값으로 설정/


        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),"퇴근 등록",
                        employeeService.updateManageMent(managementDTO)));

        // 조회 먼저하고 회원이 오늘날짜에 출근찍혔는지
    }

//    /* 퇴근 등록 */
//    @PostMapping("management/update")
//    public ResponseEntity<ResponseDTO> updateManageMent(@RequestBody ManagementDTO managementDTO) {
//        log.info("updatemanageMent=========================>", managementDTO);
//
//        LocalDateTime currentDateTime = LocalDateTime.now();
//        LocalDate currentDate = currentDateTime.toLocalDate();
//        String yearMonthDay = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//
//        // 퇴근 가능 여부 확인
//        boolean canRegisterEndTime = employeeService.canRegisterEndTime(managementDTO.getEmpNo(), yearMonthDay);
//
//        if (canRegisterEndTime) {
//            // 퇴근 가능한 경우 퇴근 시간 설정
//            LocalTime currentTime = currentDateTime.toLocalTime();
//            String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//            managementDTO.setStartTime(formattedTime);
//            managementDTO.setEndTime(formattedTime);
//            managementDTO.setManDate(yearMonthDay); // 년월일만 설정
//
//            // 퇴근 등록
//            employeeService.updateManageMent(managementDTO);
//
//            return ResponseEntity.ok()
//                    .body(new ResponseDTO(HttpStatus.OK.value(), "퇴근 등록 완료"));
//        } else {
//            return ResponseEntity.badRequest()
//                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "이미 퇴근 등록되었거나 출근하지 않았습니다."));
//        }
//    }



//    @PostMapping("/all")
//    public ResponseEntity<ResponseDTO> insertPmEmployee(@RequestBody PmEmployeeDTO pmEmployeeDTO){
//        log.info("inserPmEmployee=========================>", pmEmployeeDTO);
//        return ResponseEntity.ok()
//                .body(new ResponseDTO(HttpStatus.OK.value(),"사원 등록 성공",
//                        employeeService.insertPmEmployee(pmEmployeeDTO)));
//    }


}

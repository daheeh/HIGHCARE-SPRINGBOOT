package com.highright.highcare.pm.controller;

import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.mypage.dto.MyProfileDTO;
import com.highright.highcare.pm.dto.*;
import com.highright.highcare.pm.entity.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /* 사원 상세 조회 */
    @GetMapping("/member/detail/{empNo}")
    public ResponseEntity<ResponseDTO> selectEmpDetail(@PathVariable int empNo){

        log.info("empName==============> {}", empNo);

        PmEmployeeDTO selectEmpDetail = employeeService.selectEmpDetail(empNo);


        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",selectEmpDetail));

    }


    /* 사원 등록 */
    @PostMapping("/member/all")
    public ResponseEntity<ResponseDTO> insertPmEmployee(@ModelAttribute PmEmployeeDTO pmEmployeeDTO){
        log.info("inserPmEmployee=========================> {}", pmEmployeeDTO);


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
    @GetMapping("management/{empNo}")
    public ResponseEntity<ResponseDTO> manageMent(@RequestParam(name = "offset", defaultValue = "1") String offset,
                                                  @PathVariable String empNo )
                                                {

        log.info("start============================================");
        log.info("offset=============================== : {}", offset);
        log.info("empNo=============================== : {}", empNo);

        int selectedEmpNo = employeeService.selectEmpNo(empNo);
        log.info("selectedEmpNo=============================== : {}", selectedEmpNo);
        int total = employeeService.selectEmployeeTotal();

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(employeeService.manageMent(cri));
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));



        Map<String, Object> map = new HashMap<>();
        // 로그인 아이디를
        map.put("manage", employeeService.manageMent(cri));
        map.put("user", employeeService.userInfo(selectedEmpNo));
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공", map));

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
        managementDTO.setStatus("출근");


        return ResponseEntity.ok()
                .body(new ResponseDTO(HttpStatus.OK.value(),"출근 등록",
                        employeeService.insertmanageMent(managementDTO)));
    }


    /* 퇴근 */
    @PostMapping("management/update")
    public ResponseEntity<ResponseDTO> updateManageMent(@RequestBody ManagementDTO managementDTO) {
        log.info("updateManageMent=========================>", managementDTO);

        // 출근 여부 확인
        String updateSuccess = employeeService.hasAttendanceRecord(managementDTO);
        System.out.println("updateSuccess ==========================================>>> " + updateSuccess);
        // 업데이트 수행
      //  boolean updateSuccess = (boolean) employeeService.updateManageMent(managementDTO);

        if (updateSuccess.equals("success")) {
            return ResponseEntity.ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(), "퇴근 등록 및 업데이트 성공",updateSuccess));
        } else {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO(HttpStatus.BAD_REQUEST.value(), "퇴근 시간 등록 및 업데이트 실패"));
        }


    }

    /* 연차 */
    @GetMapping("/annual")
    public ResponseEntity<ResponseDTO> selectAnnual(@ModelAttribute AnnualDTO annualDTO,
            @RequestParam(name = "offset", defaultValue = "1") String offset){
        log.info("start========================================================");
        log.info("offset=============================== : {}", offset);

        int total = employeeService.selectEmployeeTotal();

        Criteria cri = new Criteria(Integer.valueOf(offset), 10);

        List<AnnualDTO> pmAnnuallist = employeeService.selectedAnnaul(cri);

        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
        pagingResponseDTO.setData(pmAnnuallist);
        pagingResponseDTO.setPageInfo(new PageDTO(cri, total));


        log.info("offset=============================== : {}", pmAnnuallist);

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",  pmAnnuallist));
    }
    /* 개인 연차 조회 */
    @GetMapping("/annual/detail/{empNo}")
    public ResponseEntity<ResponseDTO> selectPersonalAnnual (@ModelAttribute AnnualDTO annualDTO,
                                                             @PathVariable int empNo ){

        List<AnnualDTO> pmAnnual = employeeService.selectedPersonalAnnaul(empNo);


        log.info("offset=============================== : {}", pmAnnual);

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "조회 성공",  pmAnnual));
    }


    /* 연차 사용 */

}

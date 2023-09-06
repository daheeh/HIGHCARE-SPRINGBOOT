package com.highright.highcare.pm.service;

import com.highright.highcare.auth.entity.AUTHAccount;
import com.highright.highcare.auth.repository.AccountRepository;
import com.highright.highcare.common.Criteria;

import com.highright.highcare.pm.dto.*;
import com.highright.highcare.pm.entity.*;
import com.highright.highcare.pm.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    private final PmDepartmentRepository pmDepartmentRepository;

    private final ReEmployeeRepository reEmployeeRepository;

    private final ManagementEmRepository managementEmRepository;

    private final AccountRepository accountRepository;

    private final CareerRepository careerRepository;

    private final CertificationRepository certificationRepository;

    private final MilitaryRepository militaryRepository;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper,
                            PmDepartmentRepository pmDepartmentRepository, ReEmployeeRepository reEmployeeRepository
                            , ManagementEmRepository managementEmRepository
                            , AccountRepository accountRepository
    ,CareerRepository careerRepository
    ,CertificationRepository certificationRepository
    ,MilitaryRepository militaryRepository){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.pmDepartmentRepository = pmDepartmentRepository;
        this.reEmployeeRepository = reEmployeeRepository;
        this.managementEmRepository = managementEmRepository;
        this.accountRepository = accountRepository;
        this.careerRepository = careerRepository;
        this.certificationRepository = certificationRepository;
        this.militaryRepository = militaryRepository;
    }

    /* 토탈 */
    public int selectEmployeeTotal() {
        List<PmEmployee> pmEmployeeList = employeeRepository.findByIsResignation('N');
        log.info("size: {}", pmEmployeeList);
        log.info("size: {}", pmEmployeeList.size());

        return pmEmployeeList.size();
    }

    /* 사원 전체 조회 */
    public List<PmEmployeeDTO> selectEmployeeAllList(Criteria cri){
        System.out.println("cri ==========================> " + cri);
        int index = cri.getPageNum() -1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("isResignation").descending());
        System.out.println("paging ==========================> " + paging);

//        Page<PmEmployee> result = employeeRepository.findByIsResignation('N', paging);
        Page<PmEmployee> result = employeeRepository.findByIsResignation('N', paging);
        System.out.println("result ==========================> " + result);

        List<PmEmployeeDTO> employeeallList = result.stream()
                .map(pmEmployee -> modelMapper
                        .map(pmEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());

        return employeeallList;
    }

    /* 사원 상세 조회 */
    public PmEmployeeDTO selectEmpDetail(int empNo){

        PmEmployee empDetail = employeeRepository.findByEmpNo(empNo);
        System.out.println("result ==========================> " + empDetail);
        System.out.println("result ==========================> " + empNo);


        PmEmployeeDTO pmEmployee = modelMapper.map(empDetail, PmEmployeeDTO.class);
        System.out.println("pmEmployee ==========================> " + pmEmployee);


        return pmEmployee;
    }
    // reEmployee


    /* 사원 검색 */
    public List<PmEmployeeDTO> selectEmployeeList(String empName) {
//        log.info("empNo==================================> {}",empNo);
//        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(Integer.valueOf(empNo));
//        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(Integer.valueOf(1));
        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(Integer.valueOf(empName));
        System.out.println("pmemployeeList ==============> " + pmemployeeList);
        List<PmEmployeeDTO> employeeList = pmemployeeList.stream()
                .map(pmEmployee -> modelMapper.map(pmEmployee, PmEmployeeDTO.class))
                .collect(Collectors.toList());

        return employeeList;
    }

    /* 사원 이름 조회 */
    public int selectEmployeeTotal(String empName) {
        List<PmEmployee> pmEmployeeList = employeeRepository.findByEmpName(empName);
        log.info("size: {}", pmEmployeeList);
        log.info("size: {}", pmEmployeeList.size());

        return pmEmployeeList.size();
    }

    public int selectEmpNo(String empNo){

        Optional<AUTHAccount> authAccount = accountRepository.findById(empNo);
        System.out.println(authAccount.get().getEmployee().getEmpNo());
        int result = authAccount.get().getEmployee().getEmpNo();
        return result;
    }

//    /* 사원 번호 조회 */
//    public int selectEmpDatail(int empNo) {
//       PmEmployee pmEmployee = employeeRepository.findByEmpNo(empNo);
//        log.info("size: {}", pmEmployee);
//        log.info("size: {}", pmEmployee.size());
//
//        return pmEmployee.size();
//    }

    /* 사원 검색 */
    public List<PmEmployeeDTO> selectEmployeeSearchList(Criteria cri, String search) {
        System.out.println("selectEmployeeSearchList  cri ==========================> " + cri);
        int index = cri.getPageNum() -1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("empName").descending());
        System.out.println("selectEmployeeSearchList paging ==========================> " + paging);

        Page<PmEmployee> result = employeeRepository.findByEmpName(search, paging);
        System.out.println("selectEmployeeSearchList result ==========================> " + result);

        List<PmEmployeeDTO> employeedetailList = result.stream()
                .map(mgEmployee -> modelMapper
                        .map(mgEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());

        return employeedetailList;
    }

    /* 사원 등록 */
//    @Transactional
//    public String insertPmEmployee(@ModelAttribute EmployeeTotalDTO employeeTotalDTO) {
//        log.info("insertPmEmployee start==================");
//        log.info("insertPmEmployee pmEmployeeDTO ================== " + employeeTotalDTO );
//
//
//        int result = 0;
//
//        try {
//            PmEmployee insertPmEmployee = modelMapper.map(employeeTotalDTO, PmEmployee.class);
//            employeeRepository.save(insertPmEmployee);
//
//            result = 1;
//        }catch (Exception e){
//            System.out.println("check");
//            throw new RuntimeException(e);
//        }
//
//        log.info("insertpmEmployee ============================end");
//        return (result > 0)? "사원 등록 성공": "사원 등록 실패";
//    }

    @Transactional
    public String insertPmEmployee(@ModelAttribute EmployeeTotalDTO pmEmployeeDTO) {
        log.info("insertPmEmployee start==================");
        log.info("insertPmEmployee pmEmployeeDTO ================== " + pmEmployeeDTO );

        try {
            // PmEmployee 엔터티 생성 및 저장
            PmEmployee insertPmEmployee = modelMapper.map(pmEmployeeDTO, PmEmployee.class);
            employeeRepository.save(insertPmEmployee);

            // Career 테이블에 데이터 삽입
            for (CareerDTO careerDTO : pmEmployeeDTO.getCareer()) {
                Career career = modelMapper.map(careerDTO, Career.class);
                // PmEmployee와 연관 설정
//                career.setEmployees(insertPmEmployee);

                careerRepository.save(career);
            }

            // Certification 테이블에 데이터 삽입
            for (CertificationDTO certificationDTO : pmEmployeeDTO.getCertification()) {
                Certification certification = modelMapper.map(certificationDTO, Certification.class);
                // PmEmployee와 연관 설정
//                certification.setEmployees(insertPmEmployee);
                certificationRepository.save(certification);
            }

            // Military 테이블에 데이터 삽입
            for (MilitaryDTO militaryDTO : pmEmployeeDTO.getMilitary()) {
                Military military = modelMapper.map(militaryDTO, Military.class);
                // PmEmployee와 연관 설정
//                military.setEmployees(insertPmEmployee);
                militaryRepository.save(military);
            }

            log.info("insertpmEmployee ============================end");
            return "사원 등록 성공";
        } catch (Exception e) {
            log.error("사원 등록 실패", e);
            return "사원 등록 실패";
        }
    }



    /* 사원 및 부서 조회 // 트리뷰 */
    public PmDepartmentResult selectDept() {
        List<PmDepartment> deptList = pmDepartmentRepository.findAll();
        PmDepartmentResult result =  deptList.stream().map(PmDepartmentResult::new).collect(Collectors.toList()).get(0);
        return result;
    }



    /* 사원 수정 */
    @Transactional
    public String updateEmployee(PmEmployeeDTO pmEmployeeDTO) {
        log.info("[ProductService] updateEmployee Start ===================================");
        log.info("[ProductService] pmEmployeeDTO : " + pmEmployeeDTO);

        int result = 0;
        try {
            PmEmployee pmEmployee = employeeRepository.findById(pmEmployeeDTO.getEmpNo()).get();

            pmEmployee.setEmpName(pmEmployeeDTO.getEmpName());
            pmEmployee.setEmpEmail(pmEmployeeDTO.getEmpEmail());
            pmEmployee.setPhone(String.valueOf(pmEmployeeDTO.getIsResignation()));
            pmEmployee.setDeptCode(pmEmployeeDTO.getDeptCode());
//            pmEmployee.setJob(pmEmployeeDTO.getJobCode());
            pmEmployee.setAddress(pmEmployeeDTO.getAddress());
            pmEmployee.setEducation(pmEmployeeDTO.getEducation());
            pmEmployeeDTO.setTelephone(pmEmployeeDTO.getTelephone());

            result = 1;

        } catch (Exception e) {
            log.info("updateEmployee exception!!=====================");
            throw new RuntimeException(e);
        }
        log.info("updateEmployee end========================");

        return (result > 0)? "사원 수정 성공": "사원 수정 실패";

    }

    /* 라인 트리뷰 */
    public Object secondDept() {

        log.info("[EmployeeService] secondDept start ==================================================");

        List<PmDepartment>  pmList = pmDepartmentRepository.findAll();

        List<DeAndEmpDTO> list = new ArrayList<>();
        for(PmDepartment pm : pmList) {
            if (pm.getDeptCode() != 0) {
                DeAndEmpDTO dept = new DeAndEmpDTO();
                dept.setId(pm.getDeptCode());
                dept.setText(pm.getName());
                dept.setParent(pm.getUpperCode() == null ? 0 : pm.getUpperCode());
                dept.setDroppable(true);
                dept.setJobName(null);
                dept.setDeptName(null);

                list.add(dept);
            }
        }
        List<ReEmployee> empList = reEmployeeRepository.findAll();
        System.out.println("empList = " + empList);
        for(ReEmployee emp : empList) {
            if (emp.getEmpNo() != 0) {
                DeAndEmpDTO dept = new DeAndEmpDTO();
                dept.setId(emp.getEmpNo());
                dept.setText(emp.getEmpName() + " " + emp.getReJob().getJobName());
//                dept.setParent(emp.getDeptCode());
                dept.setParent(emp.getReDepartment().getDeptCode());
                dept.setName(emp.getEmpName());
                dept.setJobName(emp.getReJob().getJobName());
                dept.setDeptName(emp.getReDepartment().getName());




                list.add(dept);
            }
        }
        System.out.println("result===================>" + list);
        return list;
    }

    /* 출 퇴근 조회 */
    public List<ManagementResult> manageMent(Criteria cri) {
        System.out.println("cri ============================> " + cri);
        int index = cri.getPageNum() -1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("manNo").descending());
        System.out.println("paging ==========================> " + paging);

        Page<Management> result = managementEmRepository.findAll(paging);

        System.out.println("result ==========================> " + result);

        List<Management> manageList = result.getContent(); // 조회된 결과 리스트

        System.out.println("manageList ==========================> " + manageList);
        List<ManagementResult> managementResult = manageList.stream()
                .sorted(Comparator.comparing(Management::getManNo).reversed()) // manNo 내림차순 정렬
                .map(ManagementResult::new)
                .collect(Collectors.toList());


        return managementResult;
    }

    public ManagementDTO userInfo(Integer empNo) {


        Management result = managementEmRepository.findByEmpNo(empNo);

        System.out.println("result ==========================> " + result);

        ManagementDTO management = null;
        if(result != null){

            management = modelMapper.map(result, ManagementDTO.class);
            management.setStartTime(result.getStartTime());
            System.out.println("result ==========111================> " + management);
            management.setEndTime(result.getEndTime());
            System.out.println("result ===========111===============> " + management);
        }
       // Management manageList = result.get(); // 조회된 결과 리스트

        return management;
    }

//    public ManagementDTO userInfo(Integer empNo) {
//        Management result = managementEmRepository.findByEmpNo(empNo);
//
//        if (result != null) {
//            // Management 엔티티에서 startTime과 endTime 값을 가져와서 DTO에 설정
//            String startTime = result.getStartTime();
//            String endTime = result.getEndTime();
//
//            ManagementDTO managementDTO = modelMapper.map(result, ManagementDTO.class);
//
//            // DTO에 startTime와 endTime 설정
//            managementDTO.setStartTime(startTime);
//            managementDTO.setEndTime(endTime);
//
//            return managementDTO;
//        } else {
//            // Management 엔티티가 없는 경우 처리
//            return null; // 또는 에러 처리를 수행할 수 있습니다.
//        }
//    }


    /*출근시간 등록*/
    @Transactional
    public Object insertmanageMent(@ModelAttribute ManagementDTO managementDTO) {
        log.info("insertmanageMent start==================");
        log.info("insertmanageMent ManagementDTO ================== " + managementDTO );


        Optional<Management> existingRecords = managementEmRepository.findByManDateAndEmpNo(managementDTO.getManDate(), managementDTO.getEmpNo());

        if (!existingRecords.isEmpty()) {
            log.info("Record with the same manDate and empNo already exists");
            return "exit";
        }

        int result = 0;

        try {
            Management insertmanageMent =  modelMapper.map(managementDTO, Management.class);
            managementEmRepository.save(insertmanageMent);
            result = 1;
        } catch (Exception e) {
            log.error("Error while inserting management record", e);
            throw new RuntimeException(e);
        }
        log.info("RESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULTRESULT"+ result);
        log.info("insertmanageMent ============================end");
        return (result > 0) ? "stSuceess" : "fail";
    }


    /* 퇴근 */
    @Transactional
    public String updateManageMent(Management management) {
        log.info("updatemanageMent start==================");
        log.info("updatemanageMent Management ================== " + management );

        int result = 0;

        try {
           // Management updatemanageMent =  modelMapper.map(managementDTO, Management.class);
            managementEmRepository.save(management);
            result = 1;
        }catch (Exception e) {
            System.out.println("check");
            throw new RuntimeException(e);
        }
        log.info("updatemanageMent ============================end");
        return (result > 0)? "success": "fail";


    }

    /* 퇴근 조회 */
//    public List<ManagementResult> manageMentsearch(Criteria cri, int empNo) { // Remove @RequestBody
//        System.out.println("selectEmployeeSearchList  cri ==========================> " + cri);
//        System.out.println("selectEmployeeSearchList  empNo ==========================> " + empNo);
//
//        int index = cri.getPageNum() -1;
//        int count = cri.getAmount();
//
//        Pageable paging = PageRequest.of(index, count, Sort.by("empNo").descending());
//        System.out.println("selectEmployeeSearchList paging ==========================> " + paging);
//
//        Page<Management> result = managementEmRepository.findByEmpNo(empNo, paging);
//        System.out.println("selectEmployeeSearchList result ==========================> " + result);
//
//        List<Management> manageList = result.getContent(); // 조회된 결과 리스트
//
//
//
//        List<ManagementResult> manageMentsearchlist = manageList.stream()
//                .sorted(Comparator.comparing(Management::getEmpNo).reversed()) // manNo 내림차순 정렬
//                .map(ManagementResult::new)
//                .collect(Collectors.toList());
//
//        return manageMentsearchlist;
//    }

    public String hasAttendanceRecord(ManagementDTO managementDTO) {

        System.out.println("empNo =============================> " + managementDTO);

        // 현재 날짜를 구합니다.
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.println("formattedDate =============================> " + formattedDate);

        // 해당 날짜의 출근 기록을 조회합니다.
        Optional<Management> attendanceRecords = managementEmRepository.findByManDateAndEmpNo(formattedDate, managementDTO.getEmpNo());

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalTime currentTime = currentDateTime.toLocalTime();
        LocalDate currentDate2 = currentDateTime.toLocalDate();

        String yearMonthDay = currentDate2.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        // 퇴근 정보 업데이트
        //managementDTO.setManDate(yearMonthDay);
        attendanceRecords.get().setEndTime(formattedTime);
        attendanceRecords.get().setStatus("퇴근");

        String result = updateManageMent(attendanceRecords.get());
        // 출근 기록이 존재하면 true를 반환, 없으면 false를 반환합니다.

        System.out.println("result =12837129873681236712873618273621873681276832======== " + result);
        return result;
    }

    /* 연차 조회 */
//
//    public List<AnnualDTO> selectedAnnaul(Criteria cri){
//        System.out.println("cri ==========================> " + cri);
//        int index = cri.getPageNum() -1;
//        int count = cri.getAmount();
//        Pageable paging = PageRequest.of(index, count, Sort.by("empNo").descending());
//        System.out.println("paging ==========================> " + paging);
//
//
////        Page<PmEmployee> result = employeeRepository.findByIsResignation('N', paging);
//        Page<AnEmployee> result = employeeRepository.findByannaul(10005, paging);
//        System.out.println("result ==========================> " + result);
//
//        List<AnnualDTO> annualList = result.stream()
//                .map(ananul -> modelMapper
//                        .map(ananul, AnnualDTO.class)).collect(Collectors.toList());
//
//        return annualList;
//    }


}






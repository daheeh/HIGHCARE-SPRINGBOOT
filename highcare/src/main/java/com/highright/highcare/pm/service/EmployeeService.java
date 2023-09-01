package com.highright.highcare.pm.service;

import com.highright.highcare.common.Criteria;

import com.highright.highcare.pm.dto.DeAndEmpDTO;
import com.highright.highcare.pm.dto.ManagementDTO;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.*;
import com.highright.highcare.pm.repository.EmployeeRepository;
import com.highright.highcare.pm.repository.ManagementEmRepository;
import com.highright.highcare.pm.repository.PmDepartmentRepository;
import com.highright.highcare.pm.repository.ReEmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper,
                            PmDepartmentRepository pmDepartmentRepository, ReEmployeeRepository reEmployeeRepository
                            , ManagementEmRepository managementEmRepository){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.pmDepartmentRepository = pmDepartmentRepository;
        this.reEmployeeRepository = reEmployeeRepository;
        this.managementEmRepository = managementEmRepository;
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

    public int selectEmployeeTotal(String empName) {
        List<PmEmployee> pmEmployeeList = employeeRepository.findByEmpName(empName);
        log.info("size: {}", pmEmployeeList);
        log.info("size: {}", pmEmployeeList.size());

        return pmEmployeeList.size();
    }

    /* 사원 검색 */
    public List<PmEmployeeDTO> selectEmployeeSearchList(Criteria cri, String search) {
        System.out.println("selectEmployeeSearchList  cri ==========================> " + cri);
        int index = cri.getPageNum() -1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("empName").descending());
        System.out.println("selectEmployeeSearchList paging ==========================> " + paging);

        Page<MgEmployee> result = employeeRepository.findByEmpName(search, paging);
        System.out.println("selectEmployeeSearchList result ==========================> " + result);

        List<PmEmployeeDTO> employeedetailList = result.stream()
                .map(mgEmployee -> modelMapper
                        .map(mgEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());

        return employeedetailList;
    }

    /* 사원 등록 */
    @Transactional
    public String insertPmEmployee(@ModelAttribute PmEmployeeDTO pmEmployeeDTO) {
        log.info("insertPmEmployee start==================");
        log.info("insertPmEmployee pmEmployeeDTO ================== " + pmEmployeeDTO );

        int result = 0;

        try {
            PmEmployee insertPmEmployee = modelMapper.map(pmEmployeeDTO, PmEmployee.class);
            employeeRepository.save(insertPmEmployee);

            result = 1;
        }catch (Exception e){
            System.out.println("check");
            throw new RuntimeException(e);
        }

        log.info("insertpmEmployee ============================end");
        return (result > 0)? "사원 등록 성공": "사원 등록 실패";
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

//        List<ManagementDTO> managementList = result.stream()
//                .map(management -> modelMapper
//                        .map(management, ManagementDTO.class)).collect(Collectors.toList());
//
//        System.out.println("managementList =======================>" + managementList);
//
//        Long datetime = System.currentTimeMillis();
//        Timestamp timestamp = new Timestamp(datetime);
//
//        System.out.println("Datetime ======================================= " + datetime);
//        System.out.println("Timestamp:============================"+timestamp);
        // 편지
        // 우리 정렬이라는걸 해보지 않겠어???? 번호순으로 DESC해주면 아주 좋을 것같애 뭐 그렇다고
//        List<Management> manageList = managementEmRepository.findAll();
//        List<ManagementResult> managementResult =  manageList.stream().map(ManagementResult::new).collect(Collectors.toList());
//        return managementResult;

        List<Management> manageList = result.getContent(); // 조회된 결과 리스트
        List<ManagementResult> managementResult = manageList.stream()
                .sorted(Comparator.comparing(Management::getManNo).reversed()) // manNo 내림차순 정렬
                .map(ManagementResult::new)
                .collect(Collectors.toList());

        return managementResult;
    }



//    @Transactional
//    public Object insertmanageMent(@ModelAttribute ManagementDTO managementDTO) {
//        log.info("insertmanageMent start==================");
//        log.info("insertmanageMent ManagementDTO ================== " + managementDTO );
//
////        Management insertmanageMent =  modelMapper.map(managementDTO, Management.class);
////            managementEmRepository.save(insertmanageMent);
//        // 여기서 현재 날짜와 사원번호를 가지고 등록된 내용이 있으면 이미 등록된 내용이라는 반환을 해주고 아니면 등록을 진행
//
//        int result = 0;
//
//        try {
//            Management insertmanageMent =  modelMapper.map(managementDTO, Management.class);
//            managementEmRepository.save(insertmanageMent);
//            result = 1;
//        }catch (Exception e) {
//            System.out.println("check");
//            throw new RuntimeException(e);
//        }
//        log.info("insertmanageMent ============================end");
//        return (result > 0)? "출근 시간 등록 ": "출근 시간 등록 실패";
//
////        return insertmanageMent;
//    }

    /*출근시간 등록*/
    @Transactional
    public Object insertmanageMent(@ModelAttribute ManagementDTO managementDTO) {
        log.info("insertmanageMent start==================");
        log.info("insertmanageMent ManagementDTO ================== " + managementDTO );


        List<Management> existingRecords = managementEmRepository.findByManDateAndEmpNo(managementDTO.getManDate(), managementDTO.getEmpNo());

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

        log.info("insertmanageMent ============================end");
        return (result > 0) ? "success" : "fail";
    }


    /* 퇴근 */
    @Transactional
    public Object updateManageMent(@ModelAttribute ManagementDTO managementDTO) {
        log.info("updatemanageMent start==================");
        log.info("updatemanageMent ManagementDTO ================== " + managementDTO );

        int result = 0;

        try {
            Management updatemanageMent =  modelMapper.map(managementDTO, Management.class);
            managementEmRepository.save(updatemanageMent);
            result = 1;
        }catch (Exception e) {
            System.out.println("check");
            throw new RuntimeException(e);
        }
        log.info("updatemanageMent ============================end");
        return (result > 0)? "퇴근 시간 등록 ": "퇴근 시간 등록 실패";


    }
/* 퇴근 조회*/
//    public List<PmEmployeeDTO> manageMentsearch(Criteria cri, @RequestBody int empNo) {
//
//        System.out.println("selectEmployeeSearchList  cri ==========================> " + cri);
//        int index = cri.getPageNum() -1;
//        int count = cri.getAmount();
//        Pageable paging = PageRequest.of(index, count, Sort.by("empNo").descending());
//        System.out.println("selectEmployeeSearchList paging ==========================> " + paging);
//
//        Page<PmEmployeeDTO> result = employeeRepository.findByEmpNo(empNo, paging);
//        System.out.println("selectEmployeeSearchList result ==========================> " + result);
//
//        List<PmEmployeeDTO> manageMentsearchlist = result.stream()
//                .map(pmEmployee -> modelMapper
//                        .map(pmEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());
//
//        return manageMentsearchlist;
//    }

    /* 퇴근 조회 */
    public List<ManagementResult> manageMentsearch(Criteria cri, int empNo) { // Remove @RequestBody
        System.out.println("selectEmployeeSearchList  cri ==========================> " + cri);
        System.out.println("selectEmployeeSearchList  empNo ==========================> " + empNo);

        int index = cri.getPageNum() -1;
        int count = cri.getAmount();

        Pageable paging = PageRequest.of(index, count, Sort.by("empNo").descending());
        System.out.println("selectEmployeeSearchList paging ==========================> " + paging);

        Page<Management> result = managementEmRepository.findByEmpNo(empNo, paging);
        System.out.println("selectEmployeeSearchList result ==========================> " + result);

        List<Management> manageList = result.getContent(); // 조회된 결과 리스트

        List<ManagementResult> manageMentsearchlist = manageList.stream()
                .sorted(Comparator.comparing(Management::getEmpNo).reversed()) // manNo 내림차순 정렬
                .map(ManagementResult::new)
                .collect(Collectors.toList());

        return manageMentsearchlist;
    }



//    public boolean canRegisterEndTime(int empNo, String manDate) {
//        // 출근 여부 확인 로직을 추가하세요.
//        // empNo와 manDate를 사용하여 출근 여부를 확인합니다.
//        // 이미 출근했고 해당 날짜에 등록되어 있는지 등을 확인합니다.
//        // 출근 여부에 따라 true 또는 false를 반환합니다.
//        Management existingManagement = managementEmRepository.findByEmpNoAndManDate(empNo, manDate);
//
//        if (existingManagement != null) {
//            // 이미 해당 날짜에 출근 및 퇴근이 등록된 경우
//            return false;
//        }
//        return true;
//    }



//    public Object insertmanageMent(DepartmentDTO departmentDTO) {
//    }


//    public Object manageMent() {
//        Long datetime = System.currentTimeMillis();
//        Timestamp timestamp = new Timestamp(datetime);
//
//        System.out.println("Datetime ======================================= " + datetime);
//        System.out.println("Timestamp:============================"+timestamp);
//    return null;
//    }
}






package com.highright.highcare.pm.service;

import com.highright.highcare.common.Criteria;

//import com.highright.highcare.pm.dto.PmEmployeeAndDepartmentDTO;
import com.highright.highcare.pm.dto.DeAndEmpDTO;
import com.highright.highcare.pm.dto.DepartmentDTO;
import com.highright.highcare.pm.dto.ManagementDTO;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.*;
//import com.highright.highcare.pm.entity.PmEmployeeAndPmDepartment;
//import com.highright.highcare.pm.repository.DepartmentRepository;
import com.highright.highcare.pm.repository.EmployeeRepository;
import com.highright.highcare.pm.repository.PmDepartmentRepository;
//import com.highright.highcare.pm.repository.PmJobRepository;
//import com.highright.highcare.pm.repository.PmEmployeeAndPmDepartmentRepository;
//import com.highright.highcare.pm.repository.ReDepartmentRepository;
//import com.highright.highcare.pm.repository.ReEmployeeRepository;
//import com.highright.highcare.pm.repository.ReDepartmentRepository;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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



    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper,
                            PmDepartmentRepository pmDepartmentRepository, ReEmployeeRepository reEmployeeRepository
                           ){
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.pmDepartmentRepository = pmDepartmentRepository;
        this.reEmployeeRepository = reEmployeeRepository;
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


    public List<ManagementDTO> manageMent(Criteria cri) {
        System.out.println("cri ============================> " + cri);
        int index = cri.getPageNum() -1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("manNo").descending());
        System.out.println("paging ==========================> " + paging);

        Page<PmEmployee> result = employeeRepository.findByManNo("manNo", paging);
        System.out.println("result ==========================> " + result);

        List<ManagementDTO> managementList = result.stream()
                .map(management -> modelMapper
                        .map(management, ManagementDTO.class)).collect(Collectors.toList());

        Long datetime = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(datetime);

        System.out.println("Datetime ======================================= " + datetime);
        System.out.println("Timestamp:============================"+timestamp);
        return null;
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

        Page<PmEmployee> result = employeeRepository.findByEmpName(search, paging);
        System.out.println("selectEmployeeSearchList result ==========================> " + result);

        List<PmEmployeeDTO> employeedetailList = result.stream()
                .map(pmEmployee -> modelMapper
                        .map(pmEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());

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


//    public Object manageMent() {
//        Long datetime = System.currentTimeMillis();
//        Timestamp timestamp = new Timestamp(datetime);
//
//        System.out.println("Datetime ======================================= " + datetime);
//        System.out.println("Timestamp:============================"+timestamp);
//    return null;
//    }
}






package com.highright.highcare.pm.service;

import com.highright.highcare.common.Criteria;
import com.highright.highcare.pm.dto.PmDepartmentResult;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.PmEmployee;
import com.highright.highcare.pm.repository.EmployeeRepository;
import com.highright.highcare.pm.repository.PmDepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

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


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper, PmDepartmentRepository pmDepartmentRepository) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
        this.pmDepartmentRepository = pmDepartmentRepository;
    }

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
        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(Integer.valueOf(2));
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

    @Transactional(rollbackFor = Exception.class)
    public List<PmDepartmentResult> getPmDepartmentList() {


        List<PmDepartmentResult> results = pmDepartmentRepository.findAll().stream().map(PmDepartmentResult::of).collect(Collectors.toList());
        return results;
    }

//    @Transactional(rollbackFor = Exception.class)
//    public List<>



//    public List<PmEmployeeDTO> selectEmployeeList(String empNo) {
//        List<PmEmployee> pmemployeeList = employeeRepository.findByEmployee(empNo);
//
//        List<PmEmployeeDTO> employeeList = pmemployeeList.stream()
//                .map(pmEmployee -> {
//                    PmEmployeeDTO pmEmployeeDTO = modelMapper.map(pmEmployee, PmEmployeeDTO.class);
//                    return pmEmployeeDTO;
//                })
//                .collect(Collectors.toList());
//
//        return employeeList;
//    }
}





//    public List<PmEmployeeDTO> selectEmployeeList(String empNo) {
//
////        PmEmployee pmEmployee = employeeRepository.findByemployee(empNo).get();
////        EmployeeDTO employeeDTO = modelMapper.map(pmEmployee, EmployeeDTO.class);
//
////        List<EmployeeDTO> result = employeeRepository.findByemployee(empNo).get();
////        List<EmployeeDTO> employeeList = result.stream()
//
//        List<PmEmployee> pmemployeeList = employeeRepository.findByemployee(empNo);
//        List<PmEmployeeDTO> employeeList = pmemployeeList.stream()
//                .map((pmEmployee -> modelMapper
//                        .map(pmEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());
//
//        return employeeList;
//
//    }

//    @Transactional
//    public Long resist(PmEmployee pmemployee) {
//        employeeRepository.save(pmemployee);
//        return Long.valueOf(pmemployee.getEmpNo());
//
//    }
//
//    public List<PmEmployee> findEmployee(){
//        return employeeRepository.findAll();
//    }
//
//    public PmEmployee findEmployeeOne(Long empNo){
//        return employeeRepository.findOne(empNo);
//    }

// 1. 전체데이터조회 2.페이징 필요하면 리스트로뿌려주니까 페이징추가하고 3.검색어추가하고
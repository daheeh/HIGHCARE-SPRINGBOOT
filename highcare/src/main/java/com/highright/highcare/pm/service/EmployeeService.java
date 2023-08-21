package com.highright.highcare.pm.service;

import com.highright.highcare.common.Criteria;
import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.PmEmployee;
import com.highright.highcare.pm.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public int selectEmployeeTotal() {
        List<PmEmployee> pmEmployeeList = employeeRepository.findByIsResignation('N');
        log.info("size: {}", pmEmployeeList);
        log.info("size: {}", pmEmployeeList.size());

        return pmEmployeeList.size();
    }

    /* 사원 전체 조회 */
    public List<PmEmployeeDTO> selectEmployeeAllList(Criteria cri){

        int index = cri.getPageNum() -1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("isResignation").descending());

        Page<PmEmployee> result = employeeRepository.findByIsResignation('N', paging);

//        List<PmEmployee> pmEmployeeallList = employeeRepository.findAll();
//        System.out.println("pmEmployeeList ==================> " + pmEmployeeallList);
//        List<PmEmployeeDTO> pmEmployeeAllList = pmEmployeeallList.stream()
//                .map(pmEmployee -> modelMapper.map(pmEmployee, PmEmployeeDTO.class))
//                .collect(Collectors.toList());
//
//        return pmEmployeeAllList;

        List<PmEmployeeDTO> employeeallList = result.stream()
                .map(pmEmployee -> modelMapper
                        .map(pmEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());

//        return pmEmployeeallList.stream().map(pmEmployee -> modelMapper.map(pmEmployee, PmEmployeeDTO.class)).collect(Collectors.toList());

        return employeeallList;
    }

    /* 사원 상세 조회 */
    public List<PmEmployeeDTO> selectEmployeeList(String empNo) {
//        log.info("empNo==================================> {}",empNo);
//        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(Integer.valueOf(empNo));
//        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(Integer.valueOf(1));
        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(Integer.valueOf(1));
        System.out.println("pmemployeeList ==============> " + pmemployeeList);
        List<PmEmployeeDTO> employeeList = pmemployeeList.stream()
                .map(pmEmployee -> modelMapper.map(pmEmployee, PmEmployeeDTO.class))
                .collect(Collectors.toList());

        return employeeList;
    }

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
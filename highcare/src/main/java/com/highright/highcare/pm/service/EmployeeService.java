package com.highright.highcare.pm.service;

import com.highright.highcare.pm.dto.PmEmployeeDTO;
import com.highright.highcare.pm.entity.PmEmployee;
import com.highright.highcare.pm.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;


    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    public List<PmEmployeeDTO> selectEmployeeList(String empNo) {
        List<PmEmployee> pmemployeeList = employeeRepository.findByEmpNo(empNo);

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


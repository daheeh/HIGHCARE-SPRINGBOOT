package com.highright.highcare.admin.controller;


import com.highright.highcare.admin.dto.RequestMemberDTO;
import com.highright.highcare.admin.dto.UpdateAccountDTO;
import com.highright.highcare.admin.service.AdminService;
import com.highright.highcare.common.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

//    @GetMapping("main")
//    public ResponseEntity<ResponseDTO> selectAdmin(LoginMemberDTO loginMemberDTO
//            , HttpServletResponse response){
//        log.info("[AdminController] Admin : Admin ==== {}", "관리자페이지 접속 성공");
//
//
//        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
//                "관리자 페이지 접속 성공", null));
//    }
    @GetMapping("member")
    public ResponseEntity<ResponseDTO> selectMember(@RequestParam int empNo){
        log.info("empNo" , empNo);
        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "사원 조회 성공", adminService.selectMember(empNo)));
    }

    // 인서트 회원신청
    @PostMapping("/memberjoin")
    public ResponseEntity<ResponseDTO> insertAccount(@RequestBody RequestMemberDTO requestMemberDTO){
        log.info("[AdminController] insertAccount requestMemberDTO===={}", requestMemberDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "회원 등록 신청", adminService.insertAccount(requestMemberDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/memberlist")
    public ResponseEntity<ResponseDTO> selectAccountList(){

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "전체 회원 조회", adminService.selectAccountList()));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/member")
    public ResponseEntity<ResponseDTO> updateAccount( @RequestBody UpdateAccountDTO updateAccountDTO){

        log.info("[AdminController] updateAccount updateAccountDTO===={}", updateAccountDTO);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "회원 계정상태 수정", adminService.updateAccount(updateAccountDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/member/{id}")
    public ResponseEntity<ResponseDTO> updateAccount(@PathVariable String id){
        log.info("[AdminController] updateAccount id===={}", id);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(),
                "회원 삭제(탈퇴)", adminService.deleteAccount(id)));
    }






}

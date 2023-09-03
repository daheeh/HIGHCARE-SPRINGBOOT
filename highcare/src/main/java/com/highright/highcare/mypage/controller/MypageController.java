package com.highright.highcare.mypage.controller;

import com.highright.highcare.auth.dto.LoginMemberDTO;
import com.highright.highcare.common.Criteria;
import com.highright.highcare.common.PageDTO;
import com.highright.highcare.common.PagingResponseDTO;
import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.mypage.Repository.ProfileRepository;
import com.highright.highcare.mypage.dto.*;
//import com.highright.highcare.mypage.entity.AnnEmployee;
import com.highright.highcare.mypage.entity.MyProfile;
import com.highright.highcare.mypage.entity.MyProfileFile;
import com.highright.highcare.mypage.service.MypageService;
import com.highright.highcare.pm.dto.ManagementDTO;
import com.highright.highcare.pm.entity.Management;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@Slf4j
public class MypageController {

    private final MypageService mypageService;


    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    // employee와 profile을 조인한 데이터를 조회해야함
    // 현재는 파일리스트만 조회해오는것임
    // 파일을 인서트 다시 생각해보기


    @GetMapping ("/profile/{empNo}")
        public ResponseEntity<ResponseDTO> selectProfile(@PathVariable int empNo) {
        MyProfileDTO profilefileList = mypageService.selectProfilefileList(empNo);
        log.info("empNo [Controller] ================profilefileList{} ", profilefileList);

        if(profilefileList == null ){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(),  "조회결과없음"));
        }

        System.out.println("empNo [Controller] ========================== " + empNo);


        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "프로필 조회 성공", profilefileList));
        }

    @PostMapping("/update")
    public ResponseEntity<ResponseDTO> updateMyProfileFile(@ModelAttribute MyProfileFileDTO myProfileFileDTO, MultipartFile profileImage){

        log.info("insertMyProfileFile multifile!!!!!!! {}", profileImage);

//        int photoFileCodeDelete = myProfileFileDTO.getCode();

        MyProfileFileDTO updateMyProfile = mypageService.updateMyProfileFile(myProfileFileDTO, profileImage);

        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "사진 파일 등록 성공", updateMyProfile));
        //  MyProfileFileDTO updatedProfile = mypageService.updateMyProfileFile(myProfileFileDTO, profileImage);여기에서 서비스로 갔다옴
        // return에서 갔다온 파일 변수에 넘김

    }

    @GetMapping("/anselect/{empNo}")
    public ResponseEntity<ResponseDTO> annselect(@AuthenticationPrincipal LoginMemberDTO member,
                                                 @PathVariable int empNo) {
//        member.getEmpNo();
        Object annAnnual = mypageService.selectAnnList(empNo);

        log.info("[Controller] annEmployee selectAnnList^^^^^^ {}", annAnnual);

        if(annAnnual == null){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(), "조회결과없음"));
        }
        System.out.println("[Controller] annEmployee ^^^^^^" + empNo);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "연차 조회 성공", annAnnual));
    }

    @GetMapping("/manselect/{empNo}")
    public ResponseEntity<ResponseDTO> manselect(@AuthenticationPrincipal LoginMemberDTO member,
                                                 @PathVariable int empNo
//                                                 @RequestParam(name = "offset", defaultValue = "1") int offset,
//                                                 @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {

//        log.info("start=================");
//        log.info("offset=========== : {}", offset);

        Object mymanagementDTO = mypageService.selectManList(empNo);
//        int total = mypageService.manselect(empNo);     // 데이터이 총 개수 반환

        log.info("Controller] managementDTO selectManList^^^^^^ {}", mymanagementDTO);

//        Criteria cri = new Criteria(offset, limit);

//        List<ManagementDTO> data = mypageService.manselectPage(empNo);  // 페이징된 데이터 가지고 옴

//        PagingResponseDTO pagingResponseDTO = new PagingResponseDTO();
//        pagingResponseDTO.setData(mypageService.manageMent(cri));
//        pagingResponseDTO.setPageInfo((new PageDTO(cri, total)));

        if(mymanagementDTO == null) {
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(), "조회결과없음"));
        }
        System.out.println("[Controller] managementDTO %%%%" + empNo);

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "근태 조회 성공", mymanagementDTO));
    }


/* TEST를 위해 작성, 연결이 됐는지만 확인, 여기서 직접 리포지토리로 보냄 */
//    @GetMapping("/test")
//    public String Test() {
//
//        List<MyProfileFile> myProfileFiles = profileRepository.findAll();
//
//        System.out.println("myProfileFiles = " + myProfileFiles);
//
//        return "";
//    }


}



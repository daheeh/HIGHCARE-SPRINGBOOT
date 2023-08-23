package com.highright.highcare.mypage.controller;

import com.highright.highcare.common.ResponseDTO;
import com.highright.highcare.mypage.Repository.ProfileRepository;
import com.highright.highcare.mypage.dto.MyProfileDTO;
import com.highright.highcare.mypage.dto.MyProfileFileDTO;
import com.highright.highcare.mypage.entity.MyProfile;
import com.highright.highcare.mypage.entity.MyProfileFile;
import com.highright.highcare.mypage.service.MypageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@Slf4j
public class MypageController {

    private final MypageService mypageService;


    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping ("/profile/{empNo}")
        public ResponseEntity<ResponseDTO> selectProfile(@PathVariable int empNo) {

        List<MyProfileDTO> profilefileList = mypageService.selectProfilefileList(empNo);

        if(profilefileList.isEmpty()){
            return ResponseEntity
                    .ok()
                    .body(new ResponseDTO(HttpStatus.OK.value(),  "조회결과없음"));
        }

        return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value(), "프로필 조회 성공", profilefileList));
        }

    @PostMapping("/insert")
    public ResponseEntity<ResponseDTO> insertMyProfile(@RequestBody MyProfileFileDTO myProfileFileDTO){

//        MyProfileDTO savedProfile = mypageService.insertMyProfileFile(myProfileDTO);
        return ResponseEntity
                .ok()
                .body(new ResponseDTO(HttpStatus.OK.value(), "사진 파일 등록 성공", mypageService.insertMyProfileFile(myProfileFileDTO)));
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

package com.highright.highcare.mypage.service;

import com.highright.highcare.util.FileUploadUtils;
import com.highright.highcare.mypage.Repository.MyProfileFileRepository;
import com.highright.highcare.mypage.Repository.ProfileRepository;
import com.highright.highcare.mypage.dto.MyProfileDTO;
import com.highright.highcare.mypage.dto.MyProfileFileDTO;
import com.highright.highcare.mypage.entity.MyProfile;
import com.highright.highcare.mypage.entity.MyProfileFile;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MypageService {

    private final ProfileRepository profileRepository;

    private final MyProfileFileRepository myProfileFileRepository;
    private final ModelMapper modelMapper;

//    @Value("src/main/resources/static/profileimg")
//    private String IMAGE_DIR;
//    @Value("http://localhost:8080/profileimg")
//    private String IMAGE_URL;
    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
    @Value("${image.image-dir}")
    private String IMAGE_DIR;
    @Value("${image.image-url}")
    private String IMAGE_URL;

    @Autowired
    public MypageService(ProfileRepository profileRepository, MyProfileFileRepository myProfileFileRepository, ModelMapper modelMapper) {
        this.profileRepository = profileRepository;
        this.myProfileFileRepository = myProfileFileRepository;

        this.modelMapper = modelMapper;
    }


    @Transactional
    public MyProfileDTO selectProfilefileList(int empNo) {
        log.info("[MypageService] selectProfile Start =============================================");
        MyProfile ProfileList = profileRepository.findByEmpNo(empNo);
        log.info("[MypageService] selectProfilefileList ProfileList =================={}", ProfileList);
        log.info("[MypageService] selectProfile End =============================================");

        MyProfileDTO myProfileDTO = modelMapper.map(ProfileList, MyProfileDTO.class);
        log.info("[MypageService] selectProfilefileList myProfileDTO1111 =================={}", myProfileDTO);

//        myProfileDTO.setMyEmployee(modelMapper.map(ProfileList.getMyEmployee(), MyEmployeeDTO.class));
//        log.info("[MypageService] selectProfilefileList myProfileDTO222 =================={}", myProfileDTO);
        // 엔티티 디티오 이름ㅇ르 잘 안맞춰줘서 괜히 작성 ㅊ****

//        myProfileDTO.getMyEmployeeDTO().setDeptName();

//        return ProfileList.stream().map(profile -> modelMapper.map(profile, MyProfileDTO.class)).collect(Collectors.toList());
        return myProfileDTO;

    }

    // 서비스 업데이트 코드
    @Transactional
    public MyProfileFileDTO updateMyProfileFile(MyProfileFileDTO myProfileFileDTO, MultipartFile profileImage) {
        log.info("[MypageService] updateMyProfileFile --------------- start ");
        log.info("[MypageService] update MyProfileFileDTO!!@@@ " + myProfileFileDTO);

        try {
            // 기존 데이터 조회
            Optional<MyProfileFile> existingProfileFileOptional = myProfileFileRepository.findByCode(myProfileFileDTO.getCode());
            if (existingProfileFileOptional.isPresent()) {
                MyProfileFile existingProfileFile = existingProfileFileOptional.get();

                String imageName = UUID.randomUUID().toString().replace("-", "");
                String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, profileImage);

                // 기존 데이터 업데이트
                // set은 내가 디티오 엔티티에 정해준 이름, getOriginalFilename은 메소드가  정의되어있어서 getOriginalFilename를 하면
                // name에 담긴 originalFilename을 불러 올 수 있다.
                existingProfileFile.setName(profileImage.getOriginalFilename());
                existingProfileFile.setChName(replaceFileName);
                existingProfileFile.setDate(new Date(System.currentTimeMillis())); // 현재 시간 등록 (이 부분은 필요에 따라 수정)

                myProfileFileRepository.save(existingProfileFile);

                log.info("ProfileService update 이미지 oname : {}", profileImage.getOriginalFilename());
                log.info("ProfileService update 이미지 name : {}", replaceFileName);

                return myProfileFileDTO;
            } else {
                throw new RuntimeException("해당 코드의 프로필 파일을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            log.error("[MypageService] Error updateMyProfileFile : " + e.getMessage());
            throw new RuntimeException("파일 업데이트 실패");
        }
    }



}


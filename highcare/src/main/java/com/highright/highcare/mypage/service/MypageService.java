package com.highright.highcare.mypage.service;

//import com.highright.highcare.mypage.Repository.AnnRepository;
import com.highright.highcare.mypage.Repository.AnnRepository;
//import com.highright.highcare.mypage.Repository.MyManagementRepository;
import com.highright.highcare.mypage.Repository.MyManagementRepository;
import com.highright.highcare.mypage.dto.MyAnnualDTO;
import com.highright.highcare.mypage.dto.MyManegementDTO;
import com.highright.highcare.mypage.entity.*;
import com.highright.highcare.util.FileUploadUtils;
import com.highright.highcare.mypage.Repository.MyProfileFileRepository;
import com.highright.highcare.mypage.Repository.ProfileRepository;
import com.highright.highcare.mypage.dto.MyProfileDTO;
import com.highright.highcare.mypage.dto.MyProfileFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MypageService {

    private final ProfileRepository profileRepository;

    private final MyProfileFileRepository myProfileFileRepository;

    private final AnnRepository annRepository;

    private final MyManagementRepository myManagementRepository;
    private final ModelMapper modelMapper;

    /* 이미지 저장 할 위치 및 응답 할 이미지 주소 */
//    @Value("file:C:/dev/profileImages/")
    @Value("C:/dev/profileImages/")
    private String IMAGE_DIR;
    @Value("http://localhost:8080/images/")
    private String IMAGE_URL;

    @Autowired
    public MypageService(ProfileRepository profileRepository, MyProfileFileRepository myProfileFileRepository, AnnRepository annRepository, ModelMapper modelMapper, MyManagementRepository myManagementRepository) {
        this.profileRepository = profileRepository;
        this.myProfileFileRepository = myProfileFileRepository;
        this.annRepository = annRepository;
        this.myManagementRepository = myManagementRepository;

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

            System.out.println("existingProfileFileOptional =============== " + existingProfileFileOptional);
            if (existingProfileFileOptional.isPresent()) {
                MyProfileFile existingProfileFile = existingProfileFileOptional.get();

                String imageName = UUID.randomUUID().toString().replace("-", "");
                String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, imageName, profileImage);
//                String imgUrl = saveProfileImageAndGetUrl(profileImage);
//                myProfileFileDTO.setProfileImgUrl(imgUrl);

                // 기존 데이터 업데이트
                // set은 내가 디티오 엔티티에 정해준 이름, getOriginalFilename은 메소드가  정의되어있어서 getOriginalFilename를 하면
                // name에 담긴 originalFilename을 불러 올 수 있다.
                existingProfileFile.setName(profileImage.getOriginalFilename());
                existingProfileFile.setChName(replaceFileName);
                existingProfileFile.setDate(new Date(System.currentTimeMillis())); // 현재 시간 등록 (이 부분은 필요에 따라 수정)
                // url 저장 코드 추가
                String imageUrl = IMAGE_URL + replaceFileName;
                existingProfileFile.setProfileImgUrl(imageUrl);

                existingProfileFile = myProfileFileRepository.save(existingProfileFile);

                log.info("ProfileService update 이미지 oname : {}", profileImage.getOriginalFilename());
                log.info("ProfileService update 이미지 name : {}", replaceFileName);
                MyProfileFileDTO result = modelMapper.map(existingProfileFile, MyProfileFileDTO.class);
                System.out.println("result = " + result);

                return result;

            } else {
                throw new RuntimeException("해당 코드의 프로필 파일을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            log.error("[MypageService] Error updateMyProfileFile : " + e.getMessage());
            throw new RuntimeException("파일 업데이트 실패");
        }
    }


    @Transactional
    public Object selectAnnList(int empNo) {
        log.info("[MyPageService] empNo^^^^222222222 {}", empNo);

        List<MyAnnual> myAnnuals = annRepository.findByEmpNo(empNo);

        log.info("[MyPageService] annEmployee^^^^222222222 {}", myAnnuals);

        List<MyAnnualDTO> myAnnualDTOList = myAnnuals.stream().map(item -> modelMapper.map(item, MyAnnualDTO.class)).collect(Collectors.toList());
        log.info("[MyPageService] MyEmployeeDTO^^^^ {}",myAnnualDTOList);

        return myAnnualDTOList;
    }

    public Object selectManList(int empNo) {
        log.info("[MyPageService] empNo%%%%%% {}", empNo);
        List<MyManegement> myManegementList = myManagementRepository.findByEmpNo(empNo);
//        List<MyManegementDTO> mymanagementDTOList = modelMapper.map(myManegementList, MyManegementDTO.class);
       List<MyManegementDTO> mymanagementDTOList = myManegementList.stream().map(item -> modelMapper.map(item, MyManegementDTO.class)).collect(Collectors.toList());


        log.info("[MyPageService] myManegementList ========== {}", myManegementList);
        log.info("[MyPageService] managementDTOList =============== {}", mymanagementDTOList);

        return mymanagementDTOList;




    }
}


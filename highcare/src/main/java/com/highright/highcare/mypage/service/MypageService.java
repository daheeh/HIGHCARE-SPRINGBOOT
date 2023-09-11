package com.highright.highcare.mypage.service;

//import com.highright.highcare.mypage.Repository.AnnRepository;
import com.highright.highcare.common.Criteria;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


        if(ProfileList == null) {

            MyProfile newProfile = new MyProfile();
            newProfile.setEmpNo(empNo);
//            newProfile.setCode(code);
//            이미 엔티티에서 자동으로 시퀀스 생성을 해놨기 때문에 수동으로 해주면 안됨
            profileRepository.save(newProfile);
        }
        MyProfileDTO myProfileDTO = modelMapper.map(ProfileList, MyProfileDTO.class);
        log.info("[MypageService] selectProfilefileList myProfileDTO1111 =================={}", myProfileDTO);


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
//                myProfileFileDTO.setProfileImgUrl(imgUrl)

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
    public List<MyAnnualDTO> selectAnnList(int empNo) {
        log.info("[MyPageService] empNo^^^^ {}", empNo);

        List<MyAnnual> myAnnuals = annRepository.findByEmpNo(empNo);

        log.info("[MyPageService] annEmployee^^^^ {}", myAnnuals);

        List<MyAnnualDTO> myAnnualDTOList = myAnnuals.stream().map(item -> modelMapper.map(item, MyAnnualDTO.class)).collect(Collectors.toList());
        log.info("[MyPageService] MyEmployeeDTO^^^^ {}",myAnnualDTOList);

        return myAnnualDTOList;
    }

    public List selectManList(int empNo) {
        log.info("[MyPageService] empNo%%%%%% {}", empNo);
        List<MyManegement> myManegementList = myManagementRepository.findByEmpNo(empNo);
//        List<MyManegementDTO> mymanagementDTOList = modelMapper.map(myManegementList, MyManegementDTO.class);
       List<MyManegementDTO> mymanagementDTOList = myManegementList.stream().map(item -> modelMapper.map(item, MyManegementDTO.class)).collect(Collectors.toList());


        log.info("[MyPageService] myManegementList ========== {}", myManegementList);
        log.info("[MyPageService] managementDTOList =============== {}", mymanagementDTOList);

        return mymanagementDTOList;
    }

    /* 토탈 */
    public int annselectTotal(int empNo) {

        log.info("[Service annselectTotal] annselectTotal --------------- start ");
        List<MyAnnual> myAnnualList = annRepository.findByEmpNo(empNo); // 회원번호 전체 정보 조회
        // 레파지토리??
        log.info("[Service annselectTotal] myAnnualList.size: {}", myAnnualList.size());
        log.info("[Service annselectTotal] annselectTotal --------------- end ");

        return myAnnualList.size();
    }

    public Object selectAnnListPaging(int empNo, Criteria cri) {
        log.info("[MypageService] selectAnnListPaging => start ======");

        int index = cri.getPageNum() - 1;   // 인데스는 0부터 시작해거 1 빼줌
        int count = cri.getAmount();        // 페이지당 아이템 수
        Pageable paging = PageRequest.of(index, count, Sort.by("apvNo").descending());

        Page<MyAnnual> result1 = annRepository.findByEmpNo(empNo, paging);
        log.info("[MypageService] selectAnnListPagin result1 =>=============" + paging);

        List<MyAnnual> resultList = (List<MyAnnual>) result1.getContent();
        // Page 객체에서 조회 결과를 추출하여 List 형태로 변환. 이 때, getContent() 메서드를 사용하여 페이지에 포함된 실제 데이터를 추출
        resultList.forEach(MyAnnual::getAnnNo);
        // resultList의 요소들을 순회하며 MyAnnual 객체의 getAnnNo 메서드를 호출

        log.info("[MypageService] selectAnnListPaging => end =============");

        return resultList.stream().map(MyAnnual -> modelMapper.map(MyAnnual, MyAnnualDTO.class)).collect(Collectors.toList());
    }

    public int manselectTotal(int empNo) {

        log.info("[Service] myManList Start ===================");

        List<MyManegement> myManList = myManagementRepository.findByEmpNo(empNo);    // 회원 전체정보 조회
        log.info("[Service] myManList.size : {}", myManList.size());

        log.info("[Service myManList] myAnnualList.size: {}", myManList.size());
        log.info("[Service] myManList End ===================");

        return myManList.size();
    }

    public Object selectManListWithPaging(int empNo, Criteria cri) {

        log.info("[Service] selectManListWithPaging Start ======");
        int index = cri.getPageNum() - 1;
        int count = cri.getAmount();
        Pageable paging = PageRequest.of(index, count, Sort.by("manNo").descending());

        Page<MyManegement> result1 = myManagementRepository.findByEmpNo(empNo, paging);
        log.info("[MypageService] selectmanListPaging result1 =>=============" + paging);

        List<MyManegement> resultList = (List<MyManegement>) result1.getContent();
        resultList.forEach(MyManegement::getManNo);

        log.info("[MypageService] selectManListWithPaging => end =======");

        return resultList.stream().map(MyAnnual -> modelMapper.map(MyAnnual, MyAnnualDTO.class)).collect(Collectors.toList());
    }
}


package com.highright.highcare.mypage.service;

import com.highright.highcare.mypage.Repository.MyProfileFileRepository;
import com.highright.highcare.mypage.Repository.ProfileRepository;
import com.highright.highcare.mypage.dto.MyEmployeeDTO;
import com.highright.highcare.mypage.dto.MyProfileDTO;
import com.highright.highcare.mypage.dto.MyProfileFileDTO;
import com.highright.highcare.mypage.entity.MyProfile;
import com.highright.highcare.mypage.entity.MyProfileFile;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MypageService {

    private final ProfileRepository profileRepository;

    private final MyProfileFileRepository myProfileFileRepository;
    private final ModelMapper modelMapper;

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

        myProfileDTO.setMyEmployeeDTO(modelMapper.map(ProfileList.getMyEmployee(), MyEmployeeDTO.class));
        log.info("[MypageService] selectProfilefileList myProfileDTO222 =================={}", myProfileDTO);

//        return ProfileList.stream().map(profile -> modelMapper.map(profile, MyProfileDTO.class)).collect(Collectors.toList());
        return myProfileDTO;

    }

    @Transactional
    public Object insertMyProfileFile(MyProfileFileDTO myProfileFileDTO) {
        log.info("[MypageService] insertMyProfileFile --------------- start ");

        try {
              MyProfileFile insertMyProfileFile= modelMapper.map(myProfileFileDTO, MyProfileFile.class);
            myProfileFileRepository.save(insertMyProfileFile);
            log.info("[MypageService] insertMyProfileFile --------------- end ");
            return "파일 등록 성공";
        } catch (Exception e){
            log.error("[MypageService] Error insertMyProfileFile : " + e.getMessage());
            return "파일 등록 실패";
        }

    }

}


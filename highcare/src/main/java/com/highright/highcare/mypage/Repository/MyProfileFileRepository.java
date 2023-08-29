package com.highright.highcare.mypage.Repository;

import com.highright.highcare.mypage.entity.MyProfile;
import com.highright.highcare.mypage.entity.MyProfileFile;
import org.springframework.data.jpa.mapping.JpaPersistentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface MyProfileFileRepository extends JpaRepository<MyProfileFile, Integer> {


    Optional<MyProfileFile> findByCode(int code);
}

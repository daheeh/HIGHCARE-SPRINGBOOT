package com.highright.highcare.auth.repository;

import com.highright.highcare.auth.entity.OauthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OauthUserRepository extends JpaRepository<OauthUser, String> {
    Optional<OauthUser> findByEmail(String email);

}

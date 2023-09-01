package com.highright.highcare.oauth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthRepository extends JpaRepository<OAuthUser, String> {
}

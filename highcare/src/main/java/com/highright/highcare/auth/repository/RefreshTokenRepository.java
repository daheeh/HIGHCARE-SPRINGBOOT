package com.highright.highcare.auth.repository;

import com.highright.highcare.auth.entity.ADMRefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<ADMRefreshToken, String> {


}

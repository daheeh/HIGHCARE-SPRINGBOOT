package com.highright.highcare.chatting.Repository;


import com.highright.highcare.auth.entity.ADMAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<ADMAccount, Long> {


}

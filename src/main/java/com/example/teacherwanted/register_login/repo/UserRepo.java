package com.example.teacherwanted.register_login.repo;


import com.example.teacherwanted.register_login.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> { //繼承了JPA就可以使用JPA裡面的原生function，裡面存<實體,主鍵>
    Optional<User> findByMemAccount(String memAccount);//登入時檢查是否有此帳號、用帳號調出整筆資料
    public boolean existsByMemAccount(String memAccount);//註冊時檢查帳號是否重複
    public boolean existsByMemEmail(String memEmail);//註冊時檢查信箱是否重複
    public User findByMemEmail(String memEmail);//檢查忘記密碼填入的信箱是否存在
    public User findByResetPasswordToken(String token);//搜尋重設密碼的token

}

package com.example.teacherwanted.register_login.service;


import com.example.teacherwanted.register_login.UserNotFoundException;
import com.example.teacherwanted.register_login.entity.User;

public interface UserService {//若有人繼承這個介面，就必須在Impl裡實作這個介面裡所有的方法
    public void registerUser(User user);//註冊的方法
    public boolean checkMemAccount(String memAccount);//註冊時檢查帳號是否重複

    public boolean checkMemEmail(String memEmail);//註冊時檢查信箱是否重複
    public  void updateResetPasswordToken(String token,String memEmail) throws UserNotFoundException;
    public User get(String resetPasswordToken);
    public void updatePassword(User user, String newPassword);//重設密碼
}

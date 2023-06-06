package com.example.teacherwanted.register_login.controller;


import com.example.teacherwanted.register_login.entity.User;
import com.example.teacherwanted.register_login.service.AES256Util;
import com.example.teacherwanted.register_login.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserRegister {

    @Autowired
    private UserService service; //注入服務(實體才需new，其他用注入)

    @GetMapping("/register")//api url
    public String register(Model model){ //註冊的方法，model:html的元素
        User user = new User(); //新建一個實例
        model.addAttribute("user",user);//抓取html的user丟到資料庫的user
        return "register";//回傳

    }
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute User user,Model model, HttpSession session) { //傳送html的user的資料丟到後端

        boolean isAccountExists = service.checkMemAccount(user.getMemAccount());
        if (isAccountExists) {
            session.setAttribute("msg", "帳號與他人重複，請重新設定");
            return "register";
        }

        boolean isEmailExists = service.checkMemEmail(user.getMemEmail());
        if (isEmailExists) {
            session.setAttribute("msg", "電子郵件與他人重複，請重新設定");
            return "register";
        }

        // 其他註冊相關邏輯...

            String password = user.getMemPassword();//取出密碼
            String encodePassWord = AES256Util.encode(password);//密碼加密
            user.setMemPassword(encodePassWord);//塞回物件存進資料庫
            service.registerUser(user);//使用了UserService中的registerUser方法

            return "registerSuccess";//回傳字串到thymeleaf>>註冊成功頁面


    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}

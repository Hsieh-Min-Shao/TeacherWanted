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
    public String register(Model model){ //註冊的方法
        User user = new User(); // 創建一個 User 物件，即一個用於傳遞數據的實例
        model.addAttribute("user", user); // 將 user 物件數據添加到模型中，前端可以通過 th:object 屬性設置表單的數據綁定目標為 user 物件。
        return "register"; // 返回 "register" 字符串，表示要轉到名為 "register" 的視圖
    }
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute User user, HttpSession session) { //傳送html填寫的user資料丟到後端

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
            String password = user.getMemPassword();//取出密碼
            String encodePassWord = AES256Util.encode(password);//密碼加密
            user.setMemPassword(encodePassWord);//塞回物件存進資料庫
            service.registerUser(user);//使用了UserService中的registerUser方法
            return "registerSuccess";//回傳字串到thymeleaf>>註冊成功頁面
    }

}

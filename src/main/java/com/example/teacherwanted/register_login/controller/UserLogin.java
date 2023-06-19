package com.example.teacherwanted.register_login.controller;



import com.example.teacherwanted.register_login.entity.User;
import com.example.teacherwanted.register_login.repo.UserRepo;
import com.example.teacherwanted.register_login.service.AES256Util;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@SessionAttributes("user")
public class UserLogin {
    @Autowired(required = true)
    private UserRepo repo;//注入repo(要從資料庫撈資料)


    @GetMapping("/login")
    public String login(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }


    @PostMapping("/userLogin")
    public String userLogin(@ModelAttribute("user") User user, HttpSession session, HttpServletResponse response, RedirectAttributes ra) {
        String xxx = user.getMemAccount(); //獲取用戶輸入的帳號
        Optional<User> userdata = repo.findByMemAccount(xxx); //從資料庫中查找帳號對應的用戶資料
//        boolean rememberMe = user.isRememberMe();
//        if (rememberMe) {
//            // 將帳號和密碼存儲在 Cookie 中
//            Cookie usernameCookie = new Cookie("username", user.getMemAccount());
//            Cookie passwordCookie = new Cookie("password", user.getMemPassword());
//            // 設置 Cookie 的有效期限，例如 30 天
//            usernameCookie.setMaxAge(30 * 24 * 60 * 60); // 30 天的秒數
//            passwordCookie.setMaxAge(30 * 24 * 60 * 60);
//            // 將 Cookie 添加到響應中
//            response.addCookie(usernameCookie);
//            response.addCookie(passwordCookie);
//            System.out.println(user.getMemAccount()+user.getMemPassword());
//        }
        if (!userdata.isPresent()) {
            return "loginFail";
        }
        String password = AES256Util.decode(userdata.get().getMemPassword()); //密碼解密
        if (userdata.isPresent() && user.getMemPassword().equals(password)) {
            System.out.println(userdata.get());

            // 在登入成功後設置 Session
            User userdata1 = userdata.get(); //將對應的用戶資料存儲在名為 "userdata1" 的變數中。
            session.setAttribute("userInfo", userdata1); //將對應的用戶資料存儲在 HttpSession
            session.setAttribute("memAccount", userdata1.getMemAccount());//將用戶的帳號資訊存在 HttpSession，以便在其他請求中使用
            ra.addFlashAttribute("msg", "登入成功，٩(◕‿◕｡)۶歡迎回來~ " + userdata.get().getMemNickname());

            return "redirect:index";
        } else {
            return "loginFail";
        }
    }

    //用來獲取session
    @GetMapping("/user/session")
    public ResponseEntity<User> getSessionInfo(@SessionAttribute("userInfo") User user) {
        System.out.println(user);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute("user")User user, HttpSession session, RedirectAttributes ra) {
        // 清除会话中的用户信息
        //session.removeAttribute("userInfo");
        session.invalidate();
        ra.addFlashAttribute("msg", "您已登出，( •́ω•̩̥̀ )下次再會~");
        System.out.println(user.getMemAccount()+" already logout");
        return "redirect:/index";
    }


    @GetMapping("/index")
    public String index() {

        return "index";
    }




}
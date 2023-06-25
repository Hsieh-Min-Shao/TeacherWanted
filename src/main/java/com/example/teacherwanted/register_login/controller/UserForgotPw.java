package com.example.teacherwanted.register_login.controller;


import com.example.teacherwanted.register_login.UserNotFoundException;
import com.example.teacherwanted.register_login.entity.User;
import com.example.teacherwanted.register_login.service.UserService;
import com.example.test.register_login.util.Utility;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
@SessionAttributes("user")
public class UserForgotPw {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender mailSender;


    @GetMapping("/forgotpassword")
    public String forgotPassword(Model model) {
        model.addAttribute("pageTitle", "忘記密碼");
        return "forgot_pw";
    }

    @ModelAttribute("user")//空的user物件充當一個載體，將用戶的輸入值傳遞給後續的處理邏輯
    public User getUserModelAttribute() {
        return new User();
    }

    @PostMapping("/forgotpassword")
    public String processForgotPassword(@ModelAttribute User user, HttpServletRequest request, Model model) {
        String memEmail = user.getMemEmail();//獲取使用者填寫的信箱
        String token = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);//生成唯一識別碼

        try {
            userService.updateResetPasswordToken(token, memEmail);//生成令牌
            String resetPaswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;//創建連結
            sendEmail(memEmail, resetPaswordLink);//發送包含重設密碼鏈接的郵件給指定的電子郵件地址
            model.addAttribute("message", "重設密碼信已寄出!");

        } catch (UserNotFoundException ex) {
            model.addAttribute("message", "查無此Email，請重新輸入");
        } catch (MessagingException e) {
            model.addAttribute("message", "傳送失敗");
        } catch (UnsupportedEncodingException e) {
            model.addAttribute("message", "傳送失敗");
        }
        return "forgot_pw";
    }

    private void sendEmail(String memEmail, String resetPaswordLink) throws UnsupportedEncodingException, jakarta.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom("TeacherWanted123@gmail.com", "Teacher Wanted");
        helper.setTo(memEmail);
        String subject = "請重新設定密碼";
        String content = "<p>親愛的用戶您好，<p>"
                + "<p>您已申請重新設定密碼，<p>"
                + "<p><b><a href=\"" + resetPaswordLink + "\">請點擊此連結前往重設密碼</a><b><p>"
                + "<p><p>"
                + "<p>懸賞啼雀團隊 敬上<p>";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    @GetMapping("/reset_password")//顯示重設密碼表單
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.get(token);
        if (user == null) {
            model.addAttribute("title", "重設密碼");
            model.addAttribute("message", "Invalid Token");
            return "message";
        }
        model.addAttribute("token", token);
        model.addAttribute("pageTitle", "重設密碼");
        return "reset_password_form";
    }

    @PostMapping("/reset_password")//執行重設密碼
    public String processResetPassword(HttpServletRequest request, Model model,@ModelAttribute("user") User viewUser) {
        String token = request.getParameter("token");//取得使用者token
        User user = userService.get(token);//透過令牌取得使用者資訊
        if (user == null) {
            model.addAttribute("title", "重設密碼");
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else{
            userService.updatePassword(user, viewUser.getMemPassword());//重設密碼
            model.addAttribute("message","設定成功! ");
            System.out.println(user.getMemAccount()+" 已重設密碼"+user.getMemPassword());
        }
        return "login";
    }

}

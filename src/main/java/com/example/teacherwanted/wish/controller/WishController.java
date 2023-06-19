package com.example.teacherwanted.wish.controller;


import com.example.teacherwanted.register_login.entity.User;
import com.example.teacherwanted.wish.WishNotFoundException;
import com.example.teacherwanted.wish.entity.Wish;
import com.example.teacherwanted.wish.service.WishService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class WishController {
    @Autowired
    private WishService service;

    @GetMapping("/wish")
    public String showWishList(Model model) {  //showWishList 方法接受一個 Model 物件作為參數，用於傳遞資料到視圖。
        List<Wish> listWish = service.listAll(); //呼叫 service 物件的 listAll 方法，獲取所有的願望清單，並將結果賦值給 listWish 變數
        model.addAttribute("listWish", listWish);//將 listWish 變數添加到 model 物件中，以便在視圖中可以使用該資料。
        return "wish";
    }

    @GetMapping("/wish/new")
    public String showNewForm(Model model, HttpSession session, RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("userInfo");
        if (currentUser != null) {
            model.addAttribute("wish", new Wish());
            model.addAttribute("pageTitle", "新增許願");
            System.out.println("memAccount: " + currentUser);
            return "wish_form";
        } else {
            ra.addFlashAttribute("message", "請先登入後再許願<(￣︶￣)>");
            return "redirect:/wish";
        }
    }

    @PostMapping("/wish/save")
    public String saveWish(@ModelAttribute("wish") Wish wish, HttpSession session, Model model, RedirectAttributes ra) {
        String xx = (String) session.getAttribute("memAccount");
        wish.setMemAccount(xx);

        service.save(wish);
        ra.addFlashAttribute("message", "許願成功＼(＾▽＾)／");
        return "redirect:/wish?message=success";
    }

    @GetMapping("/wish/edit/{wishId}")
    public String showEditForm(@PathVariable("wishId") Integer wishId, HttpSession session, Model model, RedirectAttributes ra) {
        try {
            Wish wish = service.get(wishId);

                model.addAttribute("wish", wish);
                model.addAttribute("pageTitle", "編輯許願");
                return "wish_form";

        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/wish_my";
        }
    }


    @GetMapping("/wish/delete/{wishId}")
    public String deleteWish(@PathVariable("wishId") Integer wishId, RedirectAttributes ra, HttpSession session) {
        try {
            Wish wish = service.get(wishId);

            service.delete(wishId);
            ra.addFlashAttribute("message", "許願已刪除");
        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/wish";
    }

    @GetMapping("/mywish")
    public String showMyWishList(@ModelAttribute("wish") Wish wish, Model model, HttpSession session,RedirectAttributes ra) {
        // 檢查使用者是否已登入
        User currentUser = (User) session.getAttribute("userInfo");
        if (currentUser != null) {
            // 獲取當前會員的會員ID
            String memberAccount = currentUser.getMemAccount();
            // 根據會員ID查詢該會員發布的 Wish
            List<Wish> myWishList = service.listByMemberAccount(memberAccount);
            model.addAttribute("myWishList", myWishList);
            return "wish_my";
        } else {
            ra.addFlashAttribute("message", "請先登入再查看<(￣︶￣)>");
            return "redirect:/wish";
        }

        }
//        @GetMapping("/wish/search")
//        public String searchWish(@RequestParam("keyword") String keyword, Model model) {
//            List<Wish> searchResults = service.searchByKeyword(keyword);
//            model.addAttribute("searchResults", searchResults);
//            return "wish_search";
//        }
}

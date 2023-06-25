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
    public String showWishList(Model model) {  //showWishList方法接受一個 Model 物件作為參數，用於傳遞資料到視圖。
        List<Wish> listWish = service.listAll(); //呼叫 service 物件的 listAll 方法，獲取所有的願望清單，並將結果賦值給listWish變數
        model.addAttribute("listWish", listWish);//將 listWish 變數添加到 model 物件中，以便在視圖中可以使用該資料。
        return "wish";
    }

    @GetMapping("/wish/new")
    public String showNewForm(Model model, HttpSession session, RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("userInfo");//取得用戶登入後存儲在session中的用戶資訊
        if (currentUser != null) {
            model.addAttribute("wish", new Wish());//新的Wish物件添加到模型中，以便在視圖中使用。可以用於填寫新的許願表單
            model.addAttribute("pageTitle", "新增許願");
            return "wish_form";
        } else {
            ra.addFlashAttribute("message", "請先登入後再許願<(￣︶￣)>");
            return "redirect:/wish";
        }
    }

    @PostMapping("/wish/save")
    //@ModelAttribute("wish") Wish wish的作用是將請求中名稱為 "wish"的屬性值映射到一個 Wish物件上，以便在方法中使用該物件進行後續處理，例如保存到數據庫或進行其他操作
    public String saveWish(@ModelAttribute("wish") Wish wish, HttpSession session, RedirectAttributes ra) {
        String account = (String) session.getAttribute("memAccount");//取得登入帳號
        wish.setMemAccount(account);//傳給wish做屬性
        service.save(wish);
        ra.addFlashAttribute("message", "許願成功＼(＾▽＾)／");
        return "redirect:/wish?message=success";
    }

    @GetMapping("/wish/edit/{wishId}")
    //{wishId} 是一個路徑變數，用於指定要編輯的許願的 ID
    public String showEditForm(@PathVariable("wishId") Integer wishId, Model model, RedirectAttributes ra) {
        try {
            Wish wish = service.get(wishId);//根據許願的 ID 從資料庫中取得對應的許願物件
                model.addAttribute("wish", wish);//取得的許願物件 wish 加入到模型中，以便在視圖中使用
                model.addAttribute("pageTitle", "編輯許願");
                return "wish_form";
        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/wish_my";
        }
    }


    @GetMapping("/wish/delete/{wishId}")
    //@PathVariable("wishId")會將 URL 中的路徑變數 {wishId} 的值綁定到 wishId 參數上
    public String deleteWish(@PathVariable("wishId") Integer wishId, RedirectAttributes ra) {
        try {
            service.delete(wishId);//調用service的delete方法，傳入wishId作為參數，用於刪除相應的許願
            ra.addFlashAttribute("message", "許願已刪除");
        } catch (WishNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/wish";
    }

    @GetMapping("/mywish")
    public String showMyWishList(@ModelAttribute("wish") Wish wish, Model model, HttpSession session,RedirectAttributes ra) {
        User currentUser = (User) session.getAttribute("userInfo");
        if (currentUser != null) {
            String memberAccount = currentUser.getMemAccount();// 獲取當前會員的會員ID
            List<Wish> myWishList = service.listByMemberAccount(memberAccount);  // 根據會員ID查詢該會員發布的 Wish
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

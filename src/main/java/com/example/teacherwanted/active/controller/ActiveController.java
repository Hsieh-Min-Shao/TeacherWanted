package com.example.teacherwanted.active.controller;

import com.example.teacherwanted.active.dao.MemberDaoActive;
import com.example.teacherwanted.active.model.Active;
import com.example.teacherwanted.active.model.ActiveFavorite;
import com.example.teacherwanted.active.model.ActiveOrderDetail;
import com.example.teacherwanted.active.model.MemberActive;
import com.example.teacherwanted.active.service.ActiveFavoriteService;
import com.example.teacherwanted.active.service.ActiveOrderDetailService;
import com.example.teacherwanted.active.service.ActiveService;
import com.example.teacherwanted.active.service.MemberServiceActive;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
public class ActiveController {

    @Autowired
    private ActiveService activeService;
    @Autowired
    private ActiveOrderDetailService activeOrderDetailService;
    @Autowired
    private ActiveFavoriteService activeFavoriteService;


    //    前臺操作 開始
    @Autowired
    private MemberServiceActive memberService;

    //    拿到活動相關訂單
    @GetMapping("/activeOrderList")
    public List<ActiveOrderDetail> activeOrderList(@RequestParam Integer activityId) {
        return activeOrderDetailService.findByActiveId(activityId);
    }

    //    在訂單中拿到顧客資訊
    @GetMapping("/activeOrderMemberInfo")
    public ResponseEntity<?> selectByMemId(
            @SessionAttribute(value = "MemberId", required = false) Integer memId) {
//        System.out.println(memId);
        if (memId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("無登入狀態");
        } else {
            MemberActive memberInfo = activeOrderDetailService.selectMemBerOrderInfo(memId);
            return ResponseEntity.ok(memberInfo);

        }

    }

    //    查詢有無參加訂單
    @GetMapping("/activeOrderDetail")
    public ResponseEntity<?> selectActiveOrderDetailByMemberId(
            @RequestParam Integer activityId,
            @SessionAttribute(value = "MemberId", required = false) Integer memId) {
        System.out.println(activityId);
        if (memId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("無登入狀態");
        } else {
            if (activeOrderDetailService.queryActiveOrderHistory(activityId, memId)) {
                return ResponseEntity.ok("未參加此活動");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("已參加此活動");
            }
        }


    }

    //    送出訂單
    @PostMapping("/activeOrderDetail")
    public ResponseEntity<?> insertActiveOrderDetail(@RequestBody(required = false) ActiveOrderDetail activeOrderDetail,
                                                     @SessionAttribute(value = "MemberId",
                                                             required = false) Integer memId) {
        Integer activityIdOrder = activeOrderDetail.getActivityId();
        //        查找是否已經報名和沒有超過人數
        if (activeOrderDetailService.queryActiveOrderHistory(activityIdOrder, memId) &&
                activeOrderDetailService.insert(activeOrderDetail)) {
            return ResponseEntity.ok("報名成功");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("已參加此活動");
        }
    }

    //    查詢有無收藏活動
    @GetMapping("/activityFavorite")
    public String selectActivityFavorite(
            @RequestParam Integer activityId,
            @SessionAttribute(value = "MemberId", required = false) Integer memId) {
        if (memId == null) {
            return "無登入狀態";
        } else {
            if (activeFavoriteService.queryActiveFavoriteHistory(activityId, memId)) {
                return "未收藏";
            } else {
                return "以收藏過";
            }
        }
//        return "以收藏";
    }

    //    收藏活動
    @PostMapping("/activityFavoriteAdd")
    public ResponseEntity<?> activityFavoriteAdd(@RequestBody ActiveFavorite activeFavorite,
                                                 @SessionAttribute(value = "MemberId", required = false) Integer memId) {

        if (memId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("無登入狀態");
        } else {
            activeFavorite.setMemId(memId);
            if (activeFavoriteService.queryActiveFavoriteHistory(activeFavorite.getActivityId(), memId) &&
                    activeFavoriteService.insert(activeFavorite)) {
                return ResponseEntity.ok("收藏成功");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("以收藏過");
            }
        }

    }

    //    刪除收藏活動
    @DeleteMapping("/activityFavoriteDelete")
    public String activityFavoriteDelete(@RequestParam Integer activityId,
                                         @SessionAttribute(value = "MemberId", required = false) Integer memId) {

        activeFavoriteService.deleteByIdAndMemId(activityId, memId);
        return "取消收藏成功";
    }

    //    所有活動
    @GetMapping("/activeIndex")
    public List<Active> selectAllActive(@RequestParam(required = false) String searchKeyword,
                                        @RequestParam(required = false) String activityType) {


        List<Active> activeList = activeService.selectAll(searchKeyword, activityType);
        return activeList;
    }

    //    單個活動
    @GetMapping("/active")
    public ResponseEntity<?> selectByIdActive(@RequestParam Integer activityId) {
        //  System.out.println(activityId);
        Active active = activeService.selectById(activityId);
        //  判斷狀態
        if (active != null) {
            return ResponseEntity.ok(active);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("查無此活動");
        }

    }

    //    推薦活動
    @GetMapping("/activeRecommend")
    public List<Active> recommendActivities(@RequestParam(required = false) String activityType) {
        System.out.println(activityType);


        return activeService.recommendActivities(activityType);
    }
    //    前臺操作 結束

    //    後臺操作 開始


    //    查全部活動，包含老師比對跟類別以及關鍵字
    @GetMapping("/activeBack")
    public List<Active> selectAllActiveBack(@RequestParam(required = false) String searchKeyword,
                                            @RequestParam(required = false) String activityType,
                                            @SessionAttribute(value = "TeacherSession") Integer teaId) {
        System.out.println(teaId);

        List<Active> activeList = activeService.selectBackAll(searchKeyword, activityType, teaId);
        return activeList;
    }

    //    創建活動
    @PostMapping("/activeBackAdd")
    public String insertActiveBack(@RequestBody Active active,
                                   @SessionAttribute("TeacherSession") Integer teaId) {
        active.setTeaId(teaId);
        return activeService.insert(active);
    }

    @GetMapping("/activeBackEdit")
    public Active selectByIdActiveBack(@RequestParam Integer activityId) {
        //  System.out.println(activityId);
        Active activeEdit = activeService.selectBackById(activityId);
        return activeEdit;
    }

    @PutMapping("/activeBackEdit")
    public String updateActiveBack(@RequestBody Active active,
                                   @SessionAttribute("TeacherSession") Integer teaId) {
        active.setTeaId(teaId);
        return activeService.update(active);
    }

    @PutMapping("/activeBackStatusEdit")
    public String updateActiveBackStatus(@RequestBody Active active) {
//        return activeService.update(active);
        System.out.println("activeBackStatusEdit:" + active.getActivityId());
        System.out.println("activeBackStatusEdit:" + active.getActivityStatus());

        return activeService.updateStatus(active, active.getActivityStatus());

    }

    @DeleteMapping("/activeBackDelete")
    public String deleteActiveBack(@RequestParam Integer activityId) {
        return activeService.deleteById(activityId);
    }

    //    後臺操作 結束

    //    以下是測試用

    //    會員登入狀態

    @Autowired
    private MemberDaoActive memberDaoActive;

    @PostMapping("/loginTest")
    public String easyLoginTest(@RequestBody MemberActive memberActive, HttpServletResponse response) {
        MemberActive memberActive1 = memberDaoActive.selectById(memberActive.getMemId());


        byte[] encryptedMessageBytes = new byte[0];
        try {
            String secretMessage = memberActive1.getMemPassword();
            System.out.println("Origin Message: " + secretMessage);

            /*** Set Key ***/
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(256);
            Key key = generator.generateKey();

            /*** do Encrypt ***/
            Cipher encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] secretMessageBytes = secretMessage.getBytes(StandardCharsets.UTF_8);
            encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes);
            System.out.println("AES Encrypt: " + encryptedMessageBytes);

            /*** do Decrypt ***/
            Cipher decryptCipher = Cipher.getInstance("AES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
            String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
            System.out.println("AES Decrypt: " + decryptedMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        String PasswordBase64 = Base64.getEncoder().encodeToString(encryptedMessageBytes);
        Cookie cookie = new Cookie("TeacherWantedPassword", PasswordBase64);

        // 設置過期時間，若無設置時間，其生命週期將持續到Session 過期為止
        cookie.setMaxAge(365 * 7 * 24 * 60 * 60);
        response.addCookie(cookie);

//        session.setAttribute("MemberId", memberId);
        return "會員登入成功";
    }

    @PostMapping("/activeLogin")
    public String easyLogin(@RequestBody Integer memberId, HttpSession session) {
        session.setAttribute("MemberId", memberId);
        System.out.println("會員登入成功，id="+memberId);
        return "會員登入成功";
    }

    @PostMapping("/activeBackLogin")
    public String easyBackLogin(@RequestBody Integer teaId, HttpSession session) {
        session.setAttribute("TeacherSession", teaId);
        return "管理員登入成功";
    }

    @GetMapping("/activeAllLogout")
    public String easyLogout(HttpSession session) {
        session.removeAttribute("MemberId");
        session.removeAttribute("TeacherSession");
        return "全部都以登出";
    }

    //    上傳，直接把圖片上傳至檔案夾
    @PostMapping("/your-endpoint-url")
    public String handleRequest(
            @RequestPart("file") MultipartFile file,
            @ModelAttribute Active active
    ) {
        int id = active.getTeaId();
        String folderPath = "src/main/resources/static/img/active/" + id + "/"; // 指定存儲圖片的相對路徑
        System.out.println(id);

        // 檢查資料夾是否存在，不存在則創建
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // 生成唯一的圖片檔名
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        String filePath = folderPath + uniqueFileName;

        try {
            // 將檔案保存到指定路徑
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);

            // 返回回應，包括圖片的路徑和檔名，用於存入資料庫
            return "檔案已成功上傳，保存路徑為：" + filePath + "，檔名為：" + uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            // 返回錯誤回應（這只是一個示例）
            return "檔案上傳失敗";
        }
    }
}



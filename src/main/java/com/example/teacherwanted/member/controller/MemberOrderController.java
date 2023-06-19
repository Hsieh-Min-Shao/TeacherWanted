package com.example.teacherwanted.member.controller;

import com.example.teacherwanted.active.dao.ActiveOrderDetailDao;
import com.example.teacherwanted.active.model.Active;
import com.example.teacherwanted.active.model.ActiveOrderDetail;
import com.example.teacherwanted.active.model.MemberActive;
import com.example.teacherwanted.active.service.ActiveService;
import com.example.teacherwanted.course.model.vo.CourseOrderDetailVo;
import com.example.teacherwanted.course.model.vo.CourseOrderVo;
import com.example.teacherwanted.course.model.vo.CourseVo;
import com.example.teacherwanted.course.service.CourseOrderService;
import com.example.teacherwanted.course.service.CourseService;
import com.example.teacherwanted.member.model.Member;
import com.example.teacherwanted.member.service.MemberService;
import com.example.teacherwanted.register_login.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
public class MemberOrderController {

    @Autowired
    @Qualifier("memberServiceImpl")
    private MemberService memberService;

    @Autowired
    private ActiveService activeService;

    @Autowired
    private ActiveOrderDetailDao activeOrderDetailDao;

    @Autowired
    private CourseOrderService courseOrderService;

    @Autowired
    private CourseService courseService;


    //    拿取會員購買課程
    @GetMapping("/memberLesson")
    public List<CourseOrderDetailVo> memberLesson(@SessionAttribute("userInfo") User user) {
      return   courseOrderService.getOrderDetailsByMemId(user.getMemId());
    }

    //    拿取會員購買課程的圖片
    @GetMapping("/getCourseImg")
    public CourseVo getCourseImg(@RequestParam Integer courseId) {
        return   courseService.getCourseById(courseId);
    }


    //        各項訂單相關
    @PostMapping("/init")
    public Map<String, List<ActiveOrderDetail>> init() {
        // 使用 findAll() 拿取資料
        List<ActiveOrderDetail> activeOrderDetail = activeOrderDetailDao.selectAll();

        Map<String, List<ActiveOrderDetail>> map = new HashMap();

        map.put("groupOrderDetail", activeOrderDetail);
        return map;

    }

    //     前台
    //     前台活動訂單查詢
    @PostMapping("/OrderList")
    public ResponseEntity<?> selectActiveOrderByMemId(
            @RequestBody(required = false) MemberActive memberRequest, @SessionAttribute(value = "MemberId", required = false) Integer memId) {
        // 根據memId查询相應的memberActiveOrder資料
        List<ActiveOrderDetail> activeOrderDetail = activeOrderDetailDao.selectActiveOrderDetailByMemberId(memId);

        if (activeOrderDetail != null) {
            System.out.println(activeOrderDetail);
            System.out.println("有東西");
            return ResponseEntity.ok(activeOrderDetail);
        } else {
            System.out.println("沒有東西");
            return ResponseEntity.notFound().build();
        }
    }
    //     後台
    //     後台課程訂單全部


    //     後台活動訂單全部
    @PostMapping("/backActiveOrder")
    public ResponseEntity<?> selectAllBackActiveOrder(
            @RequestBody(required = false) MemberActive memberRequest, @SessionAttribute(value = "MemberId", required = false) Integer memId) {
        // 根據memId查询相應的memberActiveOrder資料
        List<ActiveOrderDetail> selectAllBackActiveOrder = activeOrderDetailDao.selectAll();

        if (selectAllBackActiveOrder != null) {
            System.out.println(selectAllBackActiveOrder);
            System.out.println("有東西");
            return ResponseEntity.ok(selectAllBackActiveOrder);
        } else {
            System.out.println("沒有東西");
            return ResponseEntity.notFound().build();
        }
    }
    //      活動訂單編輯


    //      活動訂單刪除
    @DeleteMapping("/backActiveOrderDelete")
    public String deleteActiveOrder(@RequestBody(required = false) MemberActive memberRequest,
                                    @SessionAttribute(value = "AdminId", required = false) Integer adminId) {
        return memberService.deleteById(adminId);
    }

    //       後台商城訂單全部


    //       商城訂單編輯


    //       商城訂單刪除


}

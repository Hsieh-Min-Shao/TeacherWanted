package com.example.teacherwanted.member.service;


import com.example.teacherwanted.active.model.Active;
import com.example.teacherwanted.active.model.ActiveOrderDetail;
import com.example.teacherwanted.member.dao.MemberDao;
import com.example.teacherwanted.member.model.Member;
import com.example.teacherwanted.wish.entity.Wish;

import java.util.List;

public interface MemberService {


    //會員資料
    String update(Member member);

    Member selectById(Integer id);






    //訂單
    //課程訂單查詢

    //活動訂單查詢
    List<ActiveOrderDetail> selectActiveOrderDetailByMemberId(Integer id);

    //data 取資料方法
    List<ActiveOrderDetail> selectAll();

    //刪除
    String deleteById(Integer id);

    Member selectBackOrderById(Integer id);

    List<Wish> getWishByMemId(Integer memId);
}

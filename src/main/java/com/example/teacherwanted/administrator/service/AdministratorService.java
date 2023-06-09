package com.example.teacherwanted.administrator.service;

import com.example.teacherwanted.administrator.model.Administrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AdministratorService {
    //    新增使用者
    public int insert(Administrator administrator);
    //    刪除
    public int deleteByAdminId(Integer adminId);
    //    修改
    public int updateByAdminId(Administrator administrator);
    //    查詢全部使用者
    public List<Administrator> findAll();
    //    查詢單個使用者
    public Administrator selectByAdminId(Integer adminId);

//    登入=>儲存session =>js hidden
    public Administrator login(Administrator administrator);
//    重設密碼
public String resetPassword( Administrator administrator);
}

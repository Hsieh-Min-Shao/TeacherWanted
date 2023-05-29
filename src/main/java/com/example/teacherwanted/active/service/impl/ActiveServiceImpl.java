package com.example.teacherwanted.active.service.impl;

import com.example.teacherwanted.active.dao.ActiveDao;
import com.example.teacherwanted.active.model.Active;
import com.example.teacherwanted.active.service.ActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ActiveServiceImpl implements ActiveService {
    @Autowired
    private ActiveDao activeDao;
    //    前臺操作 開始
//    @Override
    public List<Active> selectAll(String key, String activityType) {
        if (key == null || key.isEmpty()) {
            key = "%";
        } else {
            key = "%" + key + "%";
        }

        if (activityType == null || activityType.isEmpty()) {
            activityType = null; // 或根據需要設置為默認值
        }

        return activeDao.selectAllByKeyWorldAndType(key, activityType);
    }


    //    推薦活動
    @Override
    public List<Active> recommendActivities(String activityType){
        return activeDao.recommendActivities(activityType);
    };

    //    單個活動

    @Override
    public Active selectById(Integer id) {
        Active active = activeDao.selectById(id);
        if(active!=null && active.getActivityStatus()!=0){
            return active;
        }else {
            return null;}
        }

    //    前臺操作 結束

    //    後臺操作 開始

    @Override
    @Transactional
    public String insert(Active active) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        active.setCreateTime(timestamp);

//        System.out.println("Service：" + active);
        try {
            activeDao.insert(active);
            return "新增成功";
        } catch (Exception e) {
            return "錯誤：" + e.getMessage();
        }

    }

    @Override
    @Transactional
    public String deleteById(Integer id) {
        try {
            activeDao.deleteById(id);
            return "刪除成功";
        } catch (Exception e) {
            return "錯誤：" + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String update(Active active) {
        System.out.println("TEST");
        Active activeCreateTime = activeDao.selectById(active.getActivityId());
//        System.out.println("CreateTime:"+activeCreateTime.getCreateTime());
        active.setCreateTime(activeCreateTime.getCreateTime());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        active.setUpdateTime(timestamp);
        try {
            activeDao.update(active);
            return "更新成功";
        } catch (Exception e) {
            return "錯誤：" + e.getMessage();
        }
    }

    @Override
    @Transactional
    public String updateStatus(Active active, Integer activityStatus) {
        Active activeNewStatus = activeDao.selectById(active.getActivityId());
        activeNewStatus.setActivityStatus(activityStatus);
        try {

            activeDao.updateStatus(activeNewStatus);
//            return "更新狀態成功";
            if (active.getActivityStatus() == 0) {
                return "下架成功";
            } else if (active.getActivityStatus() == 1) {
                return "上架成功";
            } else {
                return "無法操作";
            }
        } catch (Exception e) {
            return "錯誤：" + e.getMessage();
        }

    }

    @Override
    public Active selectBackById(Integer id) {
        return activeDao.selectById(id);
    }

    @Override
    public List<Active> selectBackAll(String key, String activityType, Integer teaId) {
        if (key == null || key.isEmpty()) {
            key = "%";
        } else {
            key = "%" + key + "%";
        }

        if (activityType == null || activityType.isEmpty()) {
            activityType = null; // 或根據需要設置為默認值
        }

        return activeDao.selectBackAll(key, activityType, teaId);
    }
    //    後臺操作 結束

    //    以下是測試用
}

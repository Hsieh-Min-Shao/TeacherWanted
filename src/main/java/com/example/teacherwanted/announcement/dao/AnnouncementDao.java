package com.example.teacherwanted.announcement.dao;

import com.example.teacherwanted.announcement.model.Announcement;

import java.util.List;

public interface AnnouncementDao {
    public int insert(Announcement announcement);
    public int deleteByAnnId(Integer annId);
    public int updateByAnnId(Announcement announcement);
    public Announcement selectByAnnId(Integer annId);
    public List<Announcement> findAll(String annCategory,boolean front);
//    public List<Announcement> findCategory(String annCategory);
}

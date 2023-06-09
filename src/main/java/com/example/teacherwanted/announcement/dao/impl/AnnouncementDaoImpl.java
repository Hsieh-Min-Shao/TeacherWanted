package com.example.teacherwanted.announcement.dao.impl;

import com.example.teacherwanted.administrator.model.Administrator;
import com.example.teacherwanted.announcement.dao.AnnouncementDao;
import com.example.teacherwanted.announcement.model.Announcement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AnnouncementDaoImpl implements AnnouncementDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int insert(Announcement announcement) {
        entityManager.persist(announcement);
        return announcement.getAdminId();
    }

    @Override
    public int deleteByAnnId(Integer annId) {
        Announcement announcement = entityManager.find(Announcement.class, annId);
        entityManager.remove(announcement);
        return 0;
    }

    @Override
    public int updateByAnnId(Announcement announcement) {
        entityManager.merge(announcement);
        return 1;
    }

    @Override
    public Announcement selectByAnnId(Integer annId) {
        return entityManager.find(Announcement.class, annId);
    }


//    @Override
//    public List<Announcement> findAll() {
//        final String hql = "FROM Announcement";
//        return entityManager
//                .createQuery(hql, Announcement.class)
//                .getResultList();
//    }

    @Override
    public List<Announcement> findAll(String annCategory, boolean front) {
        String sql = "SELECT * FROM Announcement WHERE 1=1";
        if (annCategory != null) {
            sql = sql + " AND ann_category = :annCategory";
        }
        if (front) {
            sql = sql + " AND ann_date < NOW()";
            sql = sql + " AND ann_status = 1";
        }
        Query nativeQuery = entityManager.createNativeQuery(sql, Announcement.class);
        if (annCategory != null) {
            nativeQuery.setParameter("annCategory", annCategory);
        }
        return nativeQuery.getResultList();
    }
}

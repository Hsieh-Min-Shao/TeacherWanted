package com.example.teacherwanted.wish.service;

import com.example.teacherwanted.wish.WishNotFoundException;
import com.example.teacherwanted.wish.entity.Wish;
import com.example.teacherwanted.wish.repo.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishServiceImpl implements WishService {
    @Autowired
    @Qualifier("wishRepository")
    private WishRepository repo;


    public List<Wish> listAll() { // 取得所有wish的方法
        return (List<Wish>) repo.findAll(); //使用 repo.findAll() 從資料庫中查詢全部
    }

    public void save(Wish wish) { // 儲存wish的方法
        repo.save(wish); //使用 repo.save(wish) 將物件保存到資料庫
    }

    public List<Wish> listByMemberAccount(String memberAccount) { // 根據會員帳號取得wish的方法
        return repo.findByMemAccount(memberAccount);
    }


    public Wish get(Integer wishId) throws WishNotFoundException { // 根據ID取得wish的方法
        Optional<Wish> result = repo.findByWishId(wishId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new WishNotFoundException("Could not find any wish with ID " + wishId);
    }

    public void delete(Integer wishId) throws WishNotFoundException  { // 刪除wish的方法
        repo.deleteById(wishId);
    }

    //    @Override
//    public List<Wish> searchByKeyword(String keyword) {
//        return repo.searchByKeyword(keyword);
//    }


}

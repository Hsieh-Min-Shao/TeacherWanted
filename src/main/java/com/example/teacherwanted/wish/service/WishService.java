package com.example.teacherwanted.wish.service;


import com.example.teacherwanted.wish.WishNotFoundException;
import com.example.teacherwanted.wish.entity.Wish;


import java.util.List;


public interface WishService {


    public List<Wish> listAll() ;

    public void save(Wish wish);

    public List<Wish> listByMemberAccount(String memberAccount) ;

    public Wish get(Integer wishId) throws WishNotFoundException;

    public void delete(Integer wishId) throws WishNotFoundException ;

//    List<Wish> searchByKeyword(String keyword);
}

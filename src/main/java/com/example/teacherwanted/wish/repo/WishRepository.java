package com.example.teacherwanted.wish.repo;


import com.example.teacherwanted.wish.entity.Wish;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public interface WishRepository extends CrudRepository<Wish, Integer> {



    Optional<Wish> findByWishId(Integer wishId);

    List<Wish> findByMemAccount(String memberAccount);

//    List<Wish> searchByKeyword(String keyword);



}

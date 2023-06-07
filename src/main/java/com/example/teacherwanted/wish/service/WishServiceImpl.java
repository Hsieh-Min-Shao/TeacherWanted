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

    public WishServiceImpl(WishRepository wishRepository) {
        this.repo = wishRepository;
    }

    public List<Wish> listAll() {
        return (List<Wish>) repo.findAll();
    }

    public void save(Wish wish) {
        repo.save(wish);
    }

    public List<Wish> listByMemberAccount(String memberAccount) {
        return repo.findByMemAccount(memberAccount);
    }


    public List<Wish> searchByKeyword(String keyword) {
        return repo.searchByKeyword(keyword);
    }

    public Wish get(Integer wishId) throws WishNotFoundException {
        Optional<Wish> result = repo.findByWishId(wishId);
        if (result.isPresent()) {
            return result.get();
        }
        throw new WishNotFoundException("Could not find any wish with ID " + wishId);
    }

    public void delete(Integer wishId) throws WishNotFoundException {
        Long count = repo.countBywishId(wishId);
        if (count == null || count == 0) {
            throw new WishNotFoundException("Could not find any wish with ID " + wishId);
        }
        repo.deleteById(wishId);
    }


}

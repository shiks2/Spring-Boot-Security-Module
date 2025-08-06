package com.example.backend.demo_login.Shop;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String> {
    Shop findByUserId(String userId);
    Shop getShopByUserId(String userId);
}

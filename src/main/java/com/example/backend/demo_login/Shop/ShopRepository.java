package com.example.backend.demo_login.Shop;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopRepository extends MongoRepository<Shop, String> {
    Shop getShopByUserId(String userId);
}

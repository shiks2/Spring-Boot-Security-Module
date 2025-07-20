package com.example.backend.demo_login.Shop;

import com.example.backend.demo_login.Component.AuditDateTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.DateTimeException;
import java.time.Instant;
import java.util.List;

@Service
public class ShopService {
    private final ShopRepository shopRepository;

    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Shop addShop(Shop shop) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        shop.setCreatedBy(username);
        shop.setAuditDateTime(new AuditDateTime(Instant.now(),null,null));
        return shopRepository.save(shop);
    }

    public List<Shop> getShop() {
        return shopRepository.findAll();
    }

    public Shop updateShop(String id, Shop shopDetails) {
        return shopRepository.findById(id).map(shop -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            shop.setName(shopDetails.getName());
            shop.setDescription(shopDetails.getDescription());
            shop.setUpdatedBy(username);
            shop.getAuditDateTime().setUpdatedAt(Instant.now());

            return shopRepository.save(shop);
        }).orElse(null);
    }

    public void deleteShop(String id) {
        shopRepository.findById(id).ifPresent(shop -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            shop.setDeletedBy(username);
            shop.getAuditDateTime().setDeletedAt(Instant.now());
            shopRepository.save(shop);
        });
    }

    public Shop getShopByUserId(String userId) {
        return shopRepository.getShopByUserId(userId);
    }
}

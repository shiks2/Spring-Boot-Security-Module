package com.example.backend.demo_login.Shop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping
    public ResponseEntity<?> getShops() {
        return ResponseEntity.ok(this.shopService.getShop());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Shop> getShopByUserId(@PathVariable String userId) {
        Shop shop = shopService.getShopByUserId(userId);
        return shop != null ? ResponseEntity.ok(shop) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.addShop(shop));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable String id, @RequestBody Shop shop) {
        Shop updatedShop = shopService.updateShop(id, shop);
        return updatedShop != null ? ResponseEntity.ok(updatedShop) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable String id) {
        shopService.deleteShop(id);
        return ResponseEntity.noContent().build();
    }
}

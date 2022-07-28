package com.mgp.promo.promotion;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping(value = "/promotion")
    public ResponseEntity<Promotion> getPromotion(
            @RequestParam(required = true) int userId,
            @RequestParam(required = true) String promotionCode) {
        return ResponseEntity.ok(promotionService.getPromotionCode(userId, promotionCode));
    }

    @GetMapping(value = "/promotions/user/{id}")
    public ResponseEntity<List<Promotion>> getPromotionsByUser(@PathVariable int id) {
        return ResponseEntity.ok(promotionService.getPromotionsByUser(id));
    }

}

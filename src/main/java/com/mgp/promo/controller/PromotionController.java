package com.mgp.promo.controller;

import com.mgp.promo.model.Promotion;
import com.mgp.promo.service.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}

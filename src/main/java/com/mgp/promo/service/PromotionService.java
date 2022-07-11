package com.mgp.promo.service;

import com.mgp.promo.exception.PromotionNotApplicableForUserException;
import com.mgp.promo.exception.PromotionNotPresentException;
import com.mgp.promo.exception.PromotionValidityPeriodException;
import com.mgp.promo.model.Promotion;
import com.mgp.promo.repository.PromotionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@AllArgsConstructor
public class PromotionService {

    private PromotionRepository promotionRepository;

    private static final ZoneId ZONE_WARSAW = ZoneId.of("Europe/Warsaw");

    public Promotion getPromotionCode(int userId, String promotionCode) {
        Promotion promotion = promotionRepository.findByPromotionCode(promotionCode).orElseThrow(() ->
                new PromotionNotPresentException(promotionCode));
        validateUserId(promotion, userId);
        validateDates(promotion);
        return promotion;
    }

    private void validateUserId(Promotion promotion, int userId) {
        if (userId != promotion.getUser().getId()) {
            throw new PromotionNotApplicableForUserException(promotion.getPromotionCode(), userId);
        }
    }

    private void validateDates(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now(ZONE_WARSAW);
        if (promotion.getStartDate().isAfter(now) || promotion.getEndDate().isBefore(now)) {
            throw new PromotionValidityPeriodException(
                    promotion.getPromotionCode(),
                    promotion.getStartDate().toString(),
                    promotion.getEndDate().toString());
        }
    }

}

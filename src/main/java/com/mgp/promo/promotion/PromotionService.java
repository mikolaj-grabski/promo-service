package com.mgp.promo.promotion;

import com.mgp.promo.promotion.exception.PromotionNotApplicableForUserException;
import com.mgp.promo.promotion.exception.PromotionNotPresentException;
import com.mgp.promo.promotion.exception.PromotionValidityPeriodException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

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

    public List<Promotion> getPromotionsByUser(int id) {
        return promotionRepository.findAllByUserId(id);
    }
}

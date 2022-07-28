package com.mgp.promo.promotion;

import com.mgp.promo.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    Optional<Promotion> findByPromotionCode(String promotionCode);

    List<Promotion> findAllByUserId(int id);

}

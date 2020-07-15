package repository;

import model.Promo;

public interface PromoRepository extends Repository<Promo> {
    Promo getByCode(String promoCode);
}

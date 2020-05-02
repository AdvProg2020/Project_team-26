package model.repository;

import model.Promo;

public interface PromoRepository extends Repository<Promo> {
    Promo getByStringCode(String code);

}

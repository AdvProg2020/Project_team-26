package client.exception;

public enum HttpExceptionEquivalent {
    AlreadyLoggedInException(470),InvalidAuthenticationException(471), InvalidDateException(472),
    ,InvalidDiscountPercentException(473), InvalidFormatException(474),
    InvalidIdException(475),InvalidPromoCodeException(476), InvalidTokenException(477),
    NoAccessException(478),NoObjectIdException(479), NoSuchField(480),NoSuchObjectException(481),
    NotBoughtTheProductException(482),NotCustomerException(483),NotEnoughCreditException(484),
    NotEnoughProductsException(485), NotLoggedInException(486), NotSellerException(487),
    ObjectAlreadyExistException(488), PasswordIsWrongException(489),PromoNotAvailableException(490),
    WrongFieldException(491);

    private int httpCode;

    HttpExceptionEquivalent(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return this.httpCode;
    }

    public static HttpExceptionEquivalent getEquivalentException(int httpCode) {
        for (HttpExceptionEquivalent value : HttpExceptionEquivalent.values()) {
            if (value.getHttpCode() == httpCode)
                return value;
        }
        return null;
    }

}

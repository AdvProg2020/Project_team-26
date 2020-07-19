package client.exception;

public enum HttpExceptionEquivalent {
    AlreadyLoggedInException(470),InvalidAuthenticationException(471), InvalidDateException(472),
    InvalidDiscountException(473),InvalidDiscountPercentException(474), InvalidFormatException(475),
    InvalidIdException(476),InvalidPromoCodeException(477), InvalidTokenException(478),
    NoAccessException(479),NoObjectIdException(480), NoSuchField(481),NoSuchObjectException(482),
    NotBoughtTheProductException(483),NotCustomerException(484),NotEnoughProductsException(485),
    NotLoggedInException(486), NotSellerException(487),ObjectAlreadyExistException(488),
    PasswordIsWrongException(489),PromoNotAvailableException(490),WrongFieldException(491);

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

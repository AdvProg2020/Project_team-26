package Server.controller;

import exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerClass {

    @ExceptionHandler(AlreadyLoggedInException.class)
    public ResponseEntity<Exception> handleAlreadyLoggedInException(AlreadyLoggedInException e) {
        return ResponseEntity.status(470).body(e);
    }

    @ExceptionHandler(InvalidAuthenticationException.class)
    public ResponseEntity<Exception> handleInvalidAuthenticationException(InvalidAuthenticationException e) {
        return ResponseEntity.status(471).body(e);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Exception> handleInvalidDateException(InvalidDateException e) {
        return ResponseEntity.status(472).body(e);
    }

    @ExceptionHandler(InvalidDiscountPercentException.class)
    public ResponseEntity<Exception> handleInvalidDiscountPercentException(InvalidDiscountPercentException e) {
        return ResponseEntity.status(473).body(e);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Exception> handleInvalidFormatException(InvalidFormatException e) {
        return ResponseEntity.status(474).body(e);
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Exception> handleInvalidIdException(InvalidIdException e) {
        return ResponseEntity.status(475).body(e);
    }

    @ExceptionHandler(InvalidPromoCodeException.class)
    public ResponseEntity<Exception> handleInvalidPromoCodeException(InvalidPromoCodeException e) {
        return ResponseEntity.status(476).body(e);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Exception> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity.status(477).body(e);
    }

    @ExceptionHandler(NoAccessException.class)
    public ResponseEntity<Exception> handleNoAccessException(NoAccessException e) {
        return ResponseEntity.status(478).body(e);
    }

    @ExceptionHandler(NoObjectIdException.class)
    public ResponseEntity<Exception> handleNoObjectIdException(NoObjectIdException e) {
        return ResponseEntity.status(479).body(e);
    }

    @ExceptionHandler(NoSuchField.class)
    public ResponseEntity<Exception> handleNoSuchFieldException(NoSuchFieldException e) {
        return ResponseEntity.status(480).body(e);
    }

    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<Exception> handleNoSuchObjectException(NoSuchObjectException e) {
        return ResponseEntity.status(481).body(e);
    }

    @ExceptionHandler(NotBoughtTheProductException.class)
    public ResponseEntity<Exception> handleNotBoughtTheProductException(NotBoughtTheProductException e) {
        return ResponseEntity.status(482).body(e);
    }

    @ExceptionHandler(NotCustomerException.class)
    public ResponseEntity<Exception> handleNotCustomerException(NotCustomerException e) {
        return ResponseEntity.status(483).body(e);
    }

    @ExceptionHandler(NotEnoughCreditException.class)
    public ResponseEntity<Exception> handleNotEnoughCreditException(NotEnoughCreditException e) {
        return ResponseEntity.status(484).body(e);
    }

    @ExceptionHandler(NotEnoughProductsException.class)
    public ResponseEntity<Exception> handleNotEnoughProductException(NotEnoughProductsException e) {
        return ResponseEntity.status(485).body(e);
    }

    @ExceptionHandler(NotLoggedINException.class)
    public ResponseEntity<Exception> handleNotLoggedInException(NotLoggedINException e) {
        return ResponseEntity.status(486).body(e);
    }

    @ExceptionHandler(NotSellerException.class)
    public ResponseEntity<Exception> handleNotSellerException(NotSellerException e) {
        return ResponseEntity.status(487).body(e);
    }

    @ExceptionHandler(ObjectAlreadyExistException.class)
    public ResponseEntity<Exception> handleObjectAlreadyExistException(ObjectAlreadyExistException e) {
        return ResponseEntity.status(488).body(e);
    }

    @ExceptionHandler(PasswordIsWrongException.class)
    public ResponseEntity<Exception> handlePasswordIsWrongException(PasswordIsWrongException e) {
        return ResponseEntity.status(489).body(e);
    }

    @ExceptionHandler(PromoNotAvailableException.class)
    public ResponseEntity<Exception> handlePromoNotAvailableException(PromoNotAvailableException e) {
        return ResponseEntity.status(490).body(e);
    }

    @ExceptionHandler(WrongFieldException.class)
    public ResponseEntity<Exception> handleWrongFieldException(WrongFieldException e) {
        return ResponseEntity.status(491).body(e);
    }


}

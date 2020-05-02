package controller.interfaces.product;

public interface ICompareTwoItems {

    String compareItems(String firstId, String secondId, String token) throws Exceptions.TheParameterDoesNOtExist;
}

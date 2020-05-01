package controller.interfaces.product;

import controller.Exceptions;

public interface ICompareTwoItems {

    String compareItems(String firstId, String secondId, String token) throws Exceptions.TheParameterDoesNOtExist;
}

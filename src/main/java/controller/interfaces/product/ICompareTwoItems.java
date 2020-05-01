package controller.interfaces.product;

import controller.Exceptions;

public interface ICompareTwoItems {

    String compareItems(int firstId, int secondId, String token) throws Exceptions.TheParameterDoesNOtExist;
}

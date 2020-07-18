package client.gui;

import java.net.URI;

public class Constants {

    public static final Manager manager = new Manager();
    public static final int productGridColumnCount = 5;

    /**
     * AuthenticationController Methods.
     */

    public static String getAuthenticationControllerLoginAddress() {
        return manager.getHostPort() + "/controller/method/login";
    }

    public static String getAuthenticationControllerRegister() {
        return manager.getHostPort() + "/controller/method/register";
    }

    public static String getAuthenticationControllerLogoutAddress() {
        return manager.getHostPort() + "/controller/method/logout";
    }

    public static String getAuthenticationControllerDoWeHaveAManagerAddress() {
        return manager.getHostPort() + "/controller/method/do-we-have-a-manager";
    }

    /**
     * ShowUserController Methods.
     */

    public static String getShowUserControllerGetUsersAddress() {
        return manager.getHostPort() + "/controller/method/get-users";
    }

    public static String getShowUserControllerGetUserByNameAddress() {
        return manager.getHostPort() + "/controller/method/get-user-by-name";
    }

    public static String getShowUserControllerGetUserByIdAddress() {
        return manager.getHostPort() + "/controller/method/get-user-by-id";
    }

    public static String getShowUserControllerGetUserInfoAddress() {
        return manager.getHostPort() + "/controller/method/get-user-info";
    }

    public static String getShowUserControllerGetManagersAddress() {
        return manager.getHostPort() + "/controller/method/get-managers";
    }

    public static String getShowUserControllerDeleteAddress() {
        return manager.getHostPort() + "/controller/method/user/delete";
    }

    public static String getShowUserControllerGetUserByTokenAddress() {
        return manager.getHostPort() + "/controller/method/get-user-by-token";
    }

    /**
     * UserInfoController Methods.
     */

    public static String getUserInfoControllerChangePasswordWithOldPasswordAddress() {
        return manager.getHostPort() + "/controller/method/user/change-password-old-way";
    }

    public static String getUserInfoControllerChangePasswordAddress() {
        return manager.getHostPort() + "/controller/method/change-password";
    }

    public static String getUserInfoControllerChangeImageAddress() {
        return manager.getHostPort() + "/controller/method/change-image";
    }

    public static String getUserInfoControllerChangeInfoAddress() {
        return manager.getHostPort() + "/controller/method/change-info";
    }

    public static String getUserInfoControllerChangeBalanceAddress() {
        return manager.getHostPort() + "/controller/method/change-balance";
    }

    public static String getUserInfoControllerGetCompanyNameAddress() {
        return manager.getHostPort() + "/controller/method/user/get-company-name";
    }

    public static String getUserInfoControllerChangeMultipleInfoAddress() {
        return manager.getHostPort() + "/controller/method/user/change-multiple-info";
    }

    public static String getUserInfoControllerGetBalanceAddress() {
        return manager.getHostPort() + "/controller/method/user/get-balance";
    }

    public static String getUserInfoControllerGetRoleAddress() {
        return manager.getHostPort() + "/controller/method/user/get-role";
    }

    /**
     * CartController Methods.
     */

    public static String getCartControllerAddOrChangeProductAddress() {
        return manager.getHostPort() + "/controller/method/cart/add-or-change-product";
    }

    public static String getCartControllerGetCartAddress() {
        return manager.getHostPort() + "/controller/method/cart/get-cart";
    }

    public static String getCartControllerSetAddressAddress() {
        return manager.getHostPort() + "/controller/method/cart/set-address";
    }

    public static String getCartControllerUsePromoCodeAddress() {
        return manager.getHostPort() + "/controller/method/cart/use-promo-code";
    }

    public static String getCartControllerCheckoutAddress() {
        return manager.getHostPort() + "/controller/method/cart/checkout";
    }

    public static String getCartControllerChangeSellerCreditAddress() {
        return manager.getHostPort() + "/controller/method/cart/change-seller-credit";
    }

    public static String getCartControllerGetTotalPriceAddress() {
        return manager.getHostPort() + "/controller/method/cart/get-total-price";
    }

    public static String getCartControllerGetAmountInCarBySellerIdAddress() {
        return manager.getHostPort() + "/controller/method/cart/get-amount-in-cart-by-seller-id";
    }

    /**
     * OffController Methods.
     */

    public static String getOffControllerCreateNewOffAddress() {
        return manager.getHostPort() + "/controller/method/off/create-new-off";
    }

    public static String getOffControllerAddProductToOffAddress() {
        return manager.getHostPort() + "/controller/method/off/add-product-to-off";
    }

    public static String getOffControllerRemoveProductFromOffAddress() {
        return manager.getHostPort() + "/controller/method/off/remove-product-from-off";
    }

    public static String getOffControllerRemoveAOffAddress() {
        return manager.getHostPort() + "/controller/method/off/remove-a-off";
    }

    public static String getOffControllerGetAllProductsWithOffAddress() {
        return manager.getHostPort() + "/controller/method/off/get-all-products-with-off";
    }

    public static String getOffControllerGetAllOfForSellerWithFilterAddress() {
        return manager.getHostPort() + "/controller/method/off/get-all-off-for-seller-with-filter";
    }

    public static String getOffControllerGetOffAddress() {
        return manager.getHostPort() + "/controller/method/off/get-off";
    }

    public static String getOffControllerEditAddress() {
        return manager.getHostPort() + "/controller/method/off/edit";
    }

    /**
     * PromoController Methods.
     */

    public static String getPromoControllerGetPromoCodeTemplateByCodeAddress() {
        return manager.getHostPort() + "/controller/method/promo/get-promo-code-template-by-code";
    }

    public static String getPromoControllerGetPromoCodeTemplateByIdAddress() {
        return manager.getHostPort() + "/controller/method/promo/get-promo-code-template-by-id";
    }

    public static String getPromoControllerGetAllPromoCodeForCustomerAddress() {
        return manager.getHostPort() + "/controller/method/promo/get-all-promo-code-for-customer";
    }

    public static String getPromoControllerCreatePromoCodeAddress() {
        return manager.getHostPort() + "/controller/method/promo/create-promo-code";
    }

    public static String getPromoControllerRemovePromoCodeAddress() {
        return manager.getHostPort() + "/controller/method/promo/remove-promo-code";
    }

    public static String getPromoControllerAddCustomerAddress() {
        return manager.getHostPort() + "/controller/method/promo/add-customer";
    }

    public static String getPromoControllerRemoveCustomerAddress() {
        return manager.getHostPort() + "/controller/method/promo/remove-customer";
    }

    public static String getPromoControllerSetPercentAddress() {
        return manager.getHostPort() + "/controller/method/promo/set-percent";
    }

    public static String getPromoControllerSetMaxDiscountAddress() {
        return manager.getHostPort() + "/controller/method/promo/set-max-discount";
    }

    public static String getPromoControllerSetTimeAddress() {
        return manager.getHostPort() + "/controller/method/promo/set-time";
    }

    public static String getPromoControllerGetRandomPromoForUserSetAddress() {
        return manager.getHostPort() + "/controller/method/promo/get-random-promo-for-user-set";
    }

}

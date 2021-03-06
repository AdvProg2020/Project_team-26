package client.gui;


public class Constants {

    public static final Manager manager = new Manager();
    public static final int productGridColumnCount = 5;


    public static final String bankErrorInvalidPassword = "passwords do not match";
    public static final String bankErrorRepeatedUserName = "username is not available";

    public static final String bankErrorInvalidٍSource = "source account id is invalid";

    public static final String bankErrorInvalidToken = "token is invalid";

    public static final String bankErrorExpiredToken = "token expired";

    public static final String bankErrorNotEnoughInSource = "source account does not have enough money";

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

    public static String getAuthenticationControllerGetCaptchaAddress() {
        return manager.getHostPort() + "/controller/method/get-captcha";
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

    /**
     * OrderController Methods.
     */

    public static String getOrderControllerGetOrdersAddress() {
        return manager.getHostPort() + "/controller/method/order/get-orders";
    }

    public static String getOrderControllerGetOrdersWithFilterAddress() {
        return manager.getHostPort() + "/controller/method/order/get-orders-with-filters";
    }

    public static String getOrderControllerGetProductBuyerByProductIdAddress() {
        return manager.getHostPort() + "/controller/method/order/get-product-buyer-by-product-id";
    }

    public static String getOrderControllerGetASingleOrderAddress() {
        return manager.getHostPort() + "/controller/method/order/get-a-single-order";
    }

    public static String getOrderControllerGetOrderHistoryForSellerAddress() {
        return manager.getHostPort() + "/controller/method/order/get-order-history-for-seller";
    }

    public static String getOrderControllerChangeShipmentStatusAddress() {
        return manager.getHostPort() + "/controller/method/change-shipment-status";
    }

    /**
     * CategoryController Methods.
     */
    public static String getCategoryControllerGetSubCategories() {
        return manager.getHostPort() + "/controller/method/category/get-sub-category";
    }

    public static String getCategoryControllerAddCategoryAddress() {
        return manager.getHostPort() + "/controller/method/category/add-category";
    }

    public static String getCategoryControllerAddAttributeAddress() {
        return manager.getHostPort() + "/controller/method/category/add-attribute";
    }

    public static String getCategoryControllerRemoveAttributeAddress() {
        return manager.getHostPort() + "/controller/method/category/remove-attribute";
    }

    public static String getCategoryControllerRemoveACategoryAddress() {
        return manager.getHostPort() + "/controller/method/category/remove-a-category";
    }

    public static String getCategoryControllerAddProductAddress() {
        return manager.getHostPort() + "/controller/method/category/add-product";
    }

    public static String getCategoryControllerRemoveProductAddress() {
        return manager.getHostPort() + "/controller/method/category/remove-product";
    }

    public static String getCategoryControllerGetAllCategoriesAddress() {
        return manager.getHostPort() + "/controller/method/category/get-all-categories";
    }

    public static String getCategoryControllerGetAttributeAddress() {
        return manager.getHostPort() + "/controller/method/category/get-attribute";
    }

    public static String getCategoryControllerGetCategoryAddress() {
        return manager.getHostPort() + "/controller/method/category/get-category";
    }

    public static String getCategoryControllerGetCategoryByNameAddress() {
        return manager.getHostPort() + "/controller/method/category/get-name-category";
    }

    public static String getCategoryControllerGetProductsAddress() {
        return manager.getHostPort() + "/controller/method/category/get-products";
    }

    public static String getCategoryControllerGetAllProductsAddress() {
        return manager.getHostPort() + "/controller/method/category/get-all-products";
    }

    public static String getCategoryControllerGetAllProductsInOffAddress() {
        return manager.getHostPort() + "/controller/method/category/get-all-products-in-off";
    }

    public static String getCategoryControllerGetByNameAddress() {
        return manager.getHostPort() + "/controller/method/category/get-by-name";
    }

    /**
     * ProductController Methods.
     */

    public static String getProductControllerCreateProductAddress() {
        return manager.getHostPort() + "/controller/method/product/create-product";
    }

    public static String getProductControllerAddSellerAddress() {
        return manager.getHostPort() + "/controller/method/product/add-seller";
    }

    public static String getProductControllerGetProductByIdAddress() {
        return manager.getHostPort() + "/controller/method/product/get-product-by-id";
    }

    public static String getProductControllerGetProductByNameAddress() {
        return manager.getHostPort() + "/controller/method/product/get-product-by-name";
    }

    public static String getProductControllerRemoveProductAddress() {
        return manager.getHostPort() + "/controller/method/product/remove-product";
    }

    public static String getProductControllerRemoveSellerAddress() {
        return manager.getHostPort() + "/controller/method/product/remove-seller";
    }

    public static String getProductControllerGetAllProductWithFilterAddress() {
        return manager.getHostPort() + "/controller/method/product/get-all-products-with-filter";
    }

    public static String getProductControllerGetAllProductWithFilterForSellerIdAddress() {
        return manager.getHostPort() + "/controller/method/product/get-all-products-with-filter-for-seller-id";
    }

    public static String getProductControllerGetProductSellerByIdAndSellerIdAddress() {
        return manager.getHostPort() + "/controller/method/product/get-product-seller-by-id-and-seller-id";
    }

    public static String getProductControllerEditProductAddress() {
        return manager.getHostPort() + "/controller/method/product/edit-product";
    }

    public static String getProductControllerEditProductSellerAddress() {
        return manager.getHostPort() + "/controller/method/product/edit-product-seller";
    }

    public static String getProductControllerGetProductByProductSellerIdAddress() {
        return manager.getHostPort() + "/controller/method/product/get-product-by-product-seller-id";
    }

    public static String getProductControllerGetProductNameByProductSellerIdAddress() {
        return manager.getHostPort() + "/controller/method/product/get-product-name-by-product-seller-id";
    }

    public static String getProductControllerSetFileForProductAddress() {
        return manager.getHostPort() + "/controller/method/product/set-file-for-product";
    }

    public static String getProductControllerGetFileForProductAddress() {
        return manager.getHostPort() + "/controller/method/product/get-file-for-product";
    }

    /**
     * CommentController Methods.
     */

    public static String getCommentControllerAddCommentAddress() {
        return manager.getHostPort() + "/controller/method/comment/add-comment";
    }

    public static String getCommentControllerConfirmCommentAddress() {
        return manager.getHostPort() + "/controller/method/comment/confirm-comment";
    }

    public static String getCommentControllerRejectCommentAddress() {
        return manager.getHostPort() + "/controller/method/comment/reject-comment";
    }

    public static String getCommentControllerGetAllCommentsAddress() {
        return manager.getHostPort() + "/controller/method/comment/get-all-comments";
    }

    public static String getCommentControllerGetConfirmedCommentsAddress() {
        return manager.getHostPort() + "/controller/method/comment/get-confirmed-comments";
    }

    public static String getCommentControllerRemoveCommentAddress() {
        return manager.getHostPort() + "/controller/method/comment/remove-comment";
    }

    /**
     * RatingController Methods.
     */

    public static String getRatingControllerRateAddress() {
        return manager.getHostPort() + "/controller/method/rate/rate";
    }

    /**
     * RequestController Methods.
     */

    public static String getRequestControllerAcceptOffRequestAddress() {
        return manager.getHostPort() + "/controller/method/request/accept-off-request";
    }

    public static String getRequestControllerRejectOffRequestAddress() {
        return manager.getHostPort() + "/controller/method/request/reject-off-request";
    }

    public static String getRequestControllerAcceptProductRequestAddress() {
        return manager.getHostPort() + "/controller/method/request/accept-product-request";
    }

    public static String getRequestControllerRejectProductRequestAddress() {
        return manager.getHostPort() + "/controller/method/request/reject-product-request";
    }

    public static String getRequestControllerAcceptProductSellerRequestAddress() {
        return manager.getHostPort() + "/controller/method/request/accept-product-seller-request";
    }

    public static String getRequestControllerRejectProductSellerRequestAddress() {
        return manager.getHostPort() + "/controller/method/request/reject-product-seller-request";
    }

    public static String getRequestControllerGetAllRequestsAddress() {
        return manager.getHostPort() + "/controller/method/request/get-all-requests";
    }

    /**
     * SessionController Methods.
     */

    public static String getSessionControllerSetMinCreditAddress() {
        return manager.getHostPort() + "/controller/method/session/setMinCredit";
    }

    public static String getSessionControllerGetCommissionAddress() {
        return manager.getHostPort() + "/controller/method/session/getCommission";
    }

    public static String getSessionControllerSetCommissionAddress() {
        return manager.getHostPort() + "/controller/method/session/setCommission";
    }

    public static String getSessionControllerGetMinCreditAddress() {
        return manager.getHostPort() + "/controller/method/session/getMinCredit";
    }


    public static String getSessionControllerIsUserLoggedInAddress() {
        return manager.getHostPort() + "/controller/method/session/hasUserLoggedIn";
    }

    public static String getSessionControllerCreateTokenAddress() {
        return manager.getHostPort() + "/controller/method/session/getNewToken";
    }

    public static String getSessionControllerGetUserRoleAddress() {
        return manager.getHostPort() + "/controller/method/session/getUserRole";
    }

    public static String getSessionControllerGetUserAddress() {
        return manager.getHostPort() + "/controller/method/session/getLoggedInUser";
    }


    public static String getOrderControllerGetOrderItemsAddress() {
        return manager.getHostPort() + "/controller/method/get-order-items-with-order-id";
    }

    /**
     * ChatController Methods
     */
    public static String getShowUserControllerGetOnlineSupporterAddress() {
        return manager.getHostPort() + "/controller/method/get-online-support";
    }

    public static String getShowUserControllerGetAllOnlineUserAddress() {
        return manager.getHostPort() + "/controller/method/get-online-users";
    }

    public static String getAuctionControllerGetAllAuctionAddress() {
        return manager.getHostPort() + "/controller/method/request/getAll-Auction";
    }

    public static String getAuctionControllerCreateNewAuctionAddress() {
        return manager.getHostPort() + "/controller/method/request/create-newAuction";
    }

    public static String getAuctionControllerParticipateInAuctionAddress() {
        return manager.getHostPort() + "/controller/method/request/participate-in-auction";
    }

    /**
     *
     *BankController
     * @return
     */

    public static String getBankControllerCreateAccountAddress() {
        return manager.getHostPort() + "/controller/method/bank/create-account";
    }

    public static String getBankControllerChargeAccountAddress() {
        return manager.getHostPort() + "/controller/method/bank/chargeAccount";
    }

    public static String getBankControllerWithdrawFromAccountAddress() {
        return manager.getHostPort() + "/controller/method/bank/withdraw-from-account";
    }

    public static String getBankControllerGetTokenAddress() {
        return manager.getHostPort() + "/controller/method/bank/getToken";
    }
}

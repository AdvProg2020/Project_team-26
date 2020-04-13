package View.ManagerAccountView;

import View.AllProductView.AllProductView;
import View.View;

public enum ValidCommandsForManagerAccount {
    ViewPersonalInfo {
        @Override
        public String toString() {
            return "view\\s+personal\\s+info";
        }
    },
    EditTheFiled {
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },

    ManageUsers {
        @Override
        public String toString() {
            return "manage\\s+users";
        }
    },
    ManageAllProduct {
        @Override
        public String toString() {
            return "manage\\s+all\\s+products";
        }
    },
    /**
     *
     */
    RemoveProductInManageProduct {
        @Override
        public String toString() {
            return "remove\\s+(.*)";
        }
    },
    CreateDiscountCode {
        @Override
        public String toString() {
            return "create\\s+discount\\s+code";
        }
    },
    ViewAllDiscountCodes {
        @Override
        public String toString() {
            return "view\\s+discount\\s+codes";
        }
    },

    ManageRequestForManager {
        @Override
        public String toString() {
            return "manage\\s+requests";
        }
    },
    ManageCategories {
        @Override
        public String toString() {
            return "manage\\s+categories";
        }
    },
    GoToProductsMenu {
        public View getMenu() {
            return new AllProductView();
        }

        @Override
        public String toString() {
            return "products";
        }
    },

}

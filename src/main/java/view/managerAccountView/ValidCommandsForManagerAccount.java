package view.managerAccountView;

import view.managerAccountView.manageCategoryForManagerView.*;
import view.managerAccountView.manageRequestForManagerView.ManageRequestForManagerView;
import view.allProductView.AllProductView;
import view.managerAccountView.manageUsersForManager.ManageUsersForManager;
import view.View;

public enum ValidCommandsForManagerAccount {
    ViewPersonalInfo {
        public View getView() {
            return new ViewPersonalInfo();
        }

        @Override
        public String toString() {
            return "view\\s+personal\\s+info";
        }
    },
    EditTheFiled {
        public View getView() {
            return new EditTheFiled();
        }

        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },

    ManageUsers {
        public View getView() {
            return new ManageUsersForManager();
        }

        @Override
        public String toString() {
            return "manage\\s+users";
        }
    },
    ManageAllProduct {
        @Override
        public View getView() {
            return new ManageAllProductForManager();
        }

        @Override
        public String toString() {
            return "manage\\s+all\\s+products";
        }
    },
    CreateDiscountCode {
        @Override
        public View getView() {
            return new CreateDiscountCode();
        }

        @Override
        public String toString() {
            return "create\\s+discount\\s+code";
        }
    },
    ViewAllDiscountCodes {
        @Override
        public View getView() {
            return new ViewAllDiscountCodes();
        }

        @Override
        public String toString() {
            return "view\\s+discount\\s+codes";
        }
    },

    ManageRequestForManager {
        @Override
        public View getView() {
            return new ManageRequestForManagerView();
        }

        @Override
        public String toString() {
            return "manage\\s+requests";
        }
    },
    ManageCategories {
        @Override
        public View getView() {
            return new ManageCategoryForManagerView();
        }

        @Override
        public String toString() {
            return "manage\\s+categories";
        }
    },
    GoToProductsMenu {
        /***/
        @Override
        public View getView() {
            return new AllProductView();
        }

        @Override
        public String toString() {
            return "products";
        }
    },
    ;

    public abstract View getView();

}

package view.sellerAccountView;

import view.sellerAccountView.manageProductForSeller.ManageProductForSellerView;
import view.sellerAccountView.viewOffsForSellerAccount.ViewOffsForSellerAccount;
import view.View;

public enum SellerAccountViewValidCommands {

    ViewPersonalInfo {
        @Override
        public View getView() {
            return new ViewPersonalInfo();
        }
        @Override
        public String toString() {
            return "view\\s+personal\\s+info";
        }
    },
    EditTheFiled {
        @Override
        public View getView() {
            return new EditTheFiled();
        }
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },

    ViewCompanyInfo {
        @Override
        public View getView() {
            return new ViewCompanyInfo();
        }
        @Override
        public String toString() {
            return "view\\s+company\\s+information";
        }
    },

    ViewSalesHistoryForSeller {
        @Override
        public View getView() {
            return new ViewSalesHistoryForSeller();
        }
        @Override
        public String toString() {
            return "view\\s+sales\\s+history";
        }
    },

    ManageProduct {
        @Override
        public View getView() {
            return new ManageProductForSellerView();
        }

        @Override
        public String toString() {
            return "manage\\s+products";
        }
    },
    AddProductForSeller {
        @Override
        public View getView() {
            return new AddProductForSeller();
        }
        @Override
        public String toString() {
            return "add\\s+product";
        }
    },
    RemoveProductWithProductId {
        @Override
        public View getView() {
            return new RemoveProductWithProductId();
        }
        @Override
        public String toString() {
            return "remove\\s+product\\s+productId";
        }
    },
    ShowAllCategoriesForSeller {
        @Override
        public View getView() {
            return new ShowAllCategoriesForSeller();
        }
        @Override
        public String toString() {
            return "show\\s+categories";
        }
    },
    ViewAllOffsForSeller {
        @Override
        public View getView() {
            return new ViewOffsForSellerAccount();
        }

        @Override
        public String toString() {
            return "view\\s+offs";
        }
    },
    ViewBalanceForSeller {
        @Override
        public View getView() {
            return new ViewBalanceForSeller();
        }
        @Override
        public String toString() {
            return "view\\s+balance";
        }
    };

    public abstract View getView();

}

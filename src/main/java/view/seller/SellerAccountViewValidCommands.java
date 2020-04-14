package view.seller;

import view.seller.products.AddProduct;
import view.seller.products.ManageProductForSellerView;
import view.seller.offs.ShowAllOffs;
import view.View;
import view.seller.products.RemoveProduct;

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
            return new AddProduct();
        }
        @Override
        public String toString() {
            return "add\\s+product";
        }
    },
    RemoveProductWithProductId {
        @Override
        public View getView() {
            return new RemoveProduct();
        }
        @Override
        public String toString() {
            return "remove\\s+product\\s+productId";
        }
    },
    ShowAllCategoriesForSeller {
        @Override
        public View getView() {
            return new ShowAllCategories();
        }
        @Override
        public String toString() {
            return "show\\s+categories";
        }
    },
    ViewAllOffsForSeller {
        @Override
        public View getView() {
            return new ShowAllOffs();
        }

        @Override
        public String toString() {
            return "view\\s+offs";
        }
    },
    ViewBalanceForSeller {
        @Override
        public View getView() {
            return new ViewBalance();
        }
        @Override
        public String toString() {
            return "view\\s+balance";
        }
    };

    public abstract View getView();

}

package view.seller.products;

import view.View;

public enum ManageProductForSellerViewValidCommands {
    ViewProductWithId {
        @Override
        public View getView() {
            return new ShowProduct();
        }

        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },

    ViewBuyersOfProductWithId {
        @Override
        public View getView() {
            return new ShowProductBuyer();
        }

        @Override
        public String toString() {
            return "view\\s+buyers\\s+(.*)";
        }
    },
    EditProductWithId {
        @Override
        public View getView() {
            return new EditProduct();
        }

        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    };

    public abstract View getView();
}

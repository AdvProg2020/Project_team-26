package view.seller.products;

import view.View;

public enum ManageProductForSellerViewValidCommands {
    ViewProductWithId {
        @Override
        public View getView() {
            return new ViewProductWithId();
        }

        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },

    ViewBuyersOfProductWithId {
        @Override
        public View getView() {
            return new ViewBuyersOfProductWithId();
        }

        @Override
        public String toString() {
            return "view\\s+buyers\\s+(.*)";
        }
    },
    EditProductWithId {
        @Override
        public View getView() {
            return new EditProductWithId();
        }

        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    };

    public abstract View getView();
}

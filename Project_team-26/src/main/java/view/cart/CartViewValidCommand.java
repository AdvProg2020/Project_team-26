package view.cart;

import view.View;
import view.products.single.SingleProductView;

public enum CartViewValidCommand {
    ShowAllProducts {
        @Override
        public View getView() {
            return new ShowCart();
        }

        @Override
        public String toString() {
            return "show\\s+products";
        }
    },

    ShowProductWithId {
        @Override
        public View getView() {
            return new SingleProductView();
        }

        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },


    IncreaseNumberOfProductForBuyerWithId {
        @Override
        public View getView() {
            return new ChangeQuantity();
        }

        @Override
        public String toString() {
            return "increase\\s+(.*)";
        }
    },
    DecreaseNumberOfProductForBuyerWithId {
        @Override
        public View getView() {
            return new ChangeQuantity();
        }

        @Override
        public String toString() {
            return "decrease\\s+(.*)";
        }
    },
    ShowTotalPriceToBuyer {
        @Override
        public View getView() {
            return new TotalPrice();
        }

        @Override
        public String toString() {
            return "show\\s+total\\s+price";
        }
    },
    Purchase {
        @Override
        public View getView() {
            return new AddToCart();
        }

        @Override
        public String toString() {
            return "purchase";
        }
    },
    ;

    public abstract View getView();

}

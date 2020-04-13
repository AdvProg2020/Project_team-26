package View.AllProductView;

import View.*;
import View.SingleProductView.SingleProductView;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts {
        @Override
        public String toString() {
            return "view\\s+categories";
        }
    },
    Filtering {
        @Override
        public String toString() {
            return "filtering";
        }
    },
    Sorting {
        @Override
        public String toString() {
            return "sorting";
        }
    },
    ShowAllProducts {
        @Override
        public String toString() {
            return "show\\s+products";
        }
    },
    ShowProductsWithId {
        public View getView(){
            return new SingleProductView();
        }
        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    },
}


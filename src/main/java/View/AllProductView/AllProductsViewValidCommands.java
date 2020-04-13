package View.AllProductView;

import View.*;
import View.AllOffsView.FilterView.FilterView;
import View.AllOffsView.SortingView.Sort;
import View.SingleProductView.SingleProductView;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts {
        public View getView() {
            return new CategoriesOfProductsView();
        }

        @Override
        public String toString() {
            return "view\\s+categories";
        }
    },
    Filtering {
        public View getView() {
            return new FilterView();
        }

        @Override
        public String toString() {
            return "filtering";
        }
    },
    Sorting {
        public View getView() {
            return new Sort();
        }

        @Override
        public String toString() {
            return "sorting";
        }
    },
    ShowAllProducts {
        public View getView() {
            return new ShowAllProductsView();
        }

        @Override
        public String toString() {
            return "show\\s+products";
        }
    },
    ShowProductsWithId {
        public View getView() {
            return new SingleProductView();
        }

        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    },
}


package view.products.all;

import view.*;
import view.offs.filter.FilterView;
import view.offs.sort.Sort;
import view.products.single.SingleProductView;

public enum AllProductsViewValidCommands {
    ViewCategoriesOfProducts {
        @Override

        public View getView() {
            return new CategoriesOfProductsView();
        }

        @Override
        public String toString() {
            return "view\\s+categories";
        }
    },
    Filtering {
        @Override

        public View getView() {
            return new FilterView();
        }

        @Override
        public String toString() {
            return "filtering";
        }
    },
    Sorting {
        @Override

        public View getView() {
            return new Sort();
        }

        @Override
        public String toString() {
            return "sorting";
        }
    },
    ShowAllProducts {
        @Override

        public View getView() {
            return new ShowAllProductsView();
        }

        @Override
        public String toString() {
            return "show\\s+products";
        }
    },
    ShowProductsWithId {
        @Override

        public View getView() {
            return new SingleProductView();
        }

        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    },
    ;

    public abstract View getView();
}


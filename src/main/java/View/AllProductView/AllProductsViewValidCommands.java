package View.AllProductView;

import View.*;

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
        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    },
    /**
     * here is sth unusual
     */
    Digest {
        @Override
        public String toString() {
            return "digest";
        }
    },
}


package view.offs;

import view.offs.filter.FilterView;
import view.offs.sort.Sort;
import view.products.single.SingleProductView;
import view.View;

public enum AllOffsValidCommands {
    ShowProductWithId {
        @Override
        public View getView() {
            return new SingleProductView();
        }

        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
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
    Filtering {
        @Override
        public View getView() {
            return new FilterView();
        }

        @Override
        public String toString() {
            return "filtering";
        }
    };

    public abstract View getView();
}

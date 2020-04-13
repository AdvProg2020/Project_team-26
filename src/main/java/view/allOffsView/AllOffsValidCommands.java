package view.allOffsView;

import view.allOffsView.filterView.FilterView;
import view.allOffsView.sortingView.Sort;
import view.singleProductView.SingleProductView;
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

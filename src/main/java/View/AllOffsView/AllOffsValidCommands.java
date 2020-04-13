package View.AllOffsView;

import View.AllOffsView.FilterView.FilterView;
import View.AllOffsView.SortingView.Sort;
import View.SingleProductView.SingleProductView;
import View.View;

public enum AllOffsValidCommands {
    ShowProductWithId {
        public View getView() {
            return new SingleProductView();
        }

        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    },
    Sorting{
        public View getView() {
            return new Sort();
        }
        @Override
        public String toString() {
            return "sorting";
        }
    },
    Filtering{
        public View getView() {
            return new FilterView();
        }
        @Override
        public String toString() {
            return "filtering";
        }
    }
}

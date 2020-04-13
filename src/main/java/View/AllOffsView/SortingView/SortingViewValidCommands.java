package View.AllOffsView.SortingView;

import View.View;

public enum SortingViewValidCommands {
    ShowAvailbleSorts {
        public View getView() {
            return new ShowAvailbleSort();
        }

        @Override
        public String toString() {
            return "show\\s+available\\s+sorts";
        }
    },
    Sort {
        public View getView() {
            return new Sort();
        }

        @Override
        public String toString() {
            return "sort\\s+(.*)";
        }
    },
    CurrentSort {
        public View getView() {
            return new CurrentSort();
        }

        @Override
        public String toString() {
            return "current\\s+sort";
        }
    },
    DisableSort {
        public View getView() {
            return new DisableSort();
        }

        @Override
        public String toString() {
            return "disable\\s+sort";
        }
    },
}

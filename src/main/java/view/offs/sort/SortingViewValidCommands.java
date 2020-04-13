package view.offs.sort;

import view.View;

public enum SortingViewValidCommands {
    ShowAvailbleSorts {
        @Override
        public View getView() {
            return new ShowAvailbleSort();
        }

        @Override
        public String toString() {
            return "show\\s+available\\s+sorts";
        }
    },
    Sort {
        @Override
        public View getView() {
            return new Sort();
        }

        @Override
        public String toString() {
            return "sort\\s+(.*)";
        }
    },
    CurrentSort {
        @Override
        public View getView() {
            return new CurrentSort();
        }

        @Override
        public String toString() {
            return "current\\s+sort";
        }
    },
    DisableSort {
        @Override
        public View getView() {
            return new DisableSort();
        }

        @Override
        public String toString() {
            return "disable\\s+sort";
        }
    },
    ;

    public abstract View getView();
}

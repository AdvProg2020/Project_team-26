package View.AllOffsView.SortingView;

public enum SortingViewValidCommands {
    ShowAvailbleSorts {
        @Override
        public String toString() {
            return "show\\s+available\\s+sorts";
        }
    },
    Sort {
        @Override
        public String toString() {
            return "sort\\s+(.*)";
        }
    },
    CurrentSort {
        @Override
        public String toString() {
            return "current\\s+sort";
        }
    },
    DisableSort {
        @Override
        public String toString() {
            return "disable\\s+sort";
        }
    },
}

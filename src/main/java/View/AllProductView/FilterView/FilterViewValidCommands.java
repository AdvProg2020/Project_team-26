package View.AllProductView.FilterView;

import View.View;

public enum FilterViewValidCommands {
    ShowAvailableFilter {
        public View getView() {
            return new ShowAvailbleFilter();
        }

        @Override
        public String toString() {
            return "show\\s+available\\s+filters";
        }
    },
    FilterWithAvailableFilter {
        public View getView() {
            return new FilterWithAvailbleFilter();
        }

        @Override
        public String toString() {
            return "filter\\s+(.*)";
        }
    },
    ShowCurrentFilters {
        public View getView() {
            return new ShowCurrentFilters();
        }

        @Override
        public String toString() {
            return "current\\s+filters";
        }
    },
    DisableASelectedFilters {
        public View getView() {
            return new DisableSelectedFilter();
        }

        @Override
        public String toString() {
            return "disable\\s+filter\\s+(.*)";
        }
    },
}

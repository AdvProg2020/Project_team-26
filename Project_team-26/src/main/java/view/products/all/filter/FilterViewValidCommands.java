package view.products.all.filter;

import view.View;

public enum FilterViewValidCommands {
    ShowAvailableFilter {
        @Override
        public View getView() {
            return new ShowAvailbleFilter();
        }

        @Override
        public String toString() {
            return "show\\s+available\\s+filters";
        }
    },
    FilterWithAvailableFilter {
        @Override
        public View getView() {
            return new FilterWithAvailbleFilter();
        }

        @Override
        public String toString() {
            return "filter\\s+(.*)";
        }
    },
    ShowCurrentFilters {
        @Override
        public View getView() {
            return new ShowCurrentFilters();
        }

        @Override
        public String toString() {
            return "current\\s+filters";
        }
    },
    DisableASelectedFilters {
        @Override

        public View getView() {
            return new DisableSelectedFilter();
        }

        @Override
        public String toString() {
            return "disable\\s+filter\\s+(.*)";
        }
    },
    ;

    public abstract View getView();
}

package View.AllOffsView.FilterView;

public enum FilterViewValidCommands {
    ShowAvailableFilter {
        @Override
        public String toString() {
            return "show\\s+available\\s+filters";
        }
    },
    FilterWithAvailableFilter {
        @Override
        public String toString() {
            return "filter\\s+(.*)";
        }
    },
    ShowCurrentFilters {
        @Override
        public String toString() {
            return "current\\s+filters";
        }
    },
    DisableASelectedFilters {
        @Override
        public String toString() {
            return "disable\\s+filter\\s+(.*)";
        }
    },
}

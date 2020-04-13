package View.ManagerAccountView.ManageCategoryForManagerView;

public enum  ManageCategoryForManagerViewValidCommands {
    EditCategoryForManager {
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },
    AddCategoryForManager {
        @Override
        public String toString() {
            return "Add\\s+(.*)";
        }
    },
    RemoveCategoryForManager {
        @Override
        public String toString() {
            return "remove\\s+(.*)";
        }
    },

}

package view.manager.category;

import view.View;

public enum ManageCategoryForManagerViewValidCommands {
    EditCategoryForManager {
        @Override
        public View getView() {
            return new EditCategoryForManager();
        }

        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },
    AddCategoryForManager {
        @Override
        public View getView() {
            return new AddCategoryForManager();
        }

        @Override
        public String toString() {
            return "Add\\s+(.*)";
        }
    },
    RemoveCategoryForManager {
        @Override
        public View getView() {
            return new RemoveCategoryForManager();
        }

        @Override
        public String toString() {
            return "remove\\s+(.*)";
        }
    },
    ;
    public abstract View getView();

}

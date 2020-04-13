package view.managerAccountView.manageUsersForManager;

import view.View;

public enum ValidCommandsForManageUsersForManagerView {
    ViewUser {
        @Override
        public View getView() {
            return new  ViewUser();
        }
        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },
    DeleteUser {
        @Override
        public View getView() {
            return new  DeleteUser();
        }
        @Override
        public String toString() {
            return "delete\\s+user\\s+(.*)";
        }
    },
    CreateManagerProfile {
        @Override
        public View getView() {
            return new  CreateManagerProfile();
        }
        @Override
        public String toString() {
            return "create\\s+manager\\s+profile";
        }
    },
    ManageAllProduct {
        @Override
        public View getView() {
            return new ManageAllProductForUserInManagerView();
        }
        @Override
        public String toString() {
            return "manage\\s+all\\s+products";
        }
    },
    Edit {
        @Override
        public View getView() {
            return new  Edit();
        }

        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    }
    ;
    public abstract View getView();
}

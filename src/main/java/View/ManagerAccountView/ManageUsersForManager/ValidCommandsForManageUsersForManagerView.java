package View.ManagerAccountView.ManageUsersForManager;

public enum ValidCommandsForManageUsersForManagerView {
    ViewUser {
        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },
    DeleteUser {
        @Override
        public String toString() {
            return "delete\\s+user\\s+(.*)";
        }
    },
    CreateManagerProfile {
        @Override
        public String toString() {
            return "create\\s+manager\\s+profile";
        }
    },
    ManageAllProduct {
        @Override
        public String toString() {
            return "manage\\s+all\\s+products";
        }
    },
    Edit {
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    }
}

package View.ManagerAccountView.ManageRequestForManagerView;

public enum ManageRequestForManagerViewValidCommands {
    DetailOfRequest {
        @Override
        public String toString() {
            return "details\\s+(.*)";
        }
    },
    AcceptTheRequest {
        @Override
        public String toString() {
            return "accept\\s+(.*)";
        }
    },
    DeclineTheRequest {
        @Override
        public String toString() {
            return "decline\\s+(.*)";
        }
    }
}

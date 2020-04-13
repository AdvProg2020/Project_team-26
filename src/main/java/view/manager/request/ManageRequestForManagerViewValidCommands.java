package view.manager.request;

import view.View;

public enum ManageRequestForManagerViewValidCommands {
    DetailOfRequest {
        @Override
        public View getView() {
            return new DetailOfRequest();
        }
        @Override
        public String toString() {
            return "details\\s+(.*)";
        }
    },
    AcceptTheRequest {
        @Override
        public View getView() {
            return new AcceptTheRequest();
        }
        @Override
        public String toString() {
            return "accept\\s+(.*)";
        }
    },
    DeclineTheRequest {
        @Override
        public View getView() {
            return new DeclineTheRequest();
        }

        @Override
        public String toString() {
            return "decline\\s+(.*)";
        }
    }
    ;
    public abstract View getView();

}

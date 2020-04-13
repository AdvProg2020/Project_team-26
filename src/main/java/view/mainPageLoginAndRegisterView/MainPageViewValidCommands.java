package view.mainPageLoginAndRegisterView;

import view.allOffsView.AllOffsView;
import view.allProductView.AllProductView;
import view.help.HelpView;
import view.View;

public enum MainPageViewValidCommands {
    CreateAccount {
        @Override
        public View getView() {
            return new CreateAccount();
        }

        @Override
        public String toString() {
            return "create\\s+account\\s+(buyer|seller|manager)\\s+(.*)";
        }
    },
    LoginAccount {
        @Override
        public View getView() {
            return new LoginView();
        }

        @Override
        public String toString() {
            return "login\\s+(.*)";
        }
    },
    Back {
        @Override
        public View getView() {
            return null;
        }
        @Override
        public String toString() {
            return "back";
        }
    },
    Exit {
        @Override
        public View getView() {
            return null;
        }

        @Override
        public String toString() {
            return "Exit";
        }
    },
    ShowProducts {
        @Override

        public View getView() {
            return new AllProductView();
        }

        @Override
        public String toString() {
            return "products";
        }
    },
    ShowOffs {
        @Override

        public View getView() {
            return new AllOffsView();
        }

        @Override
        public String toString() {
            return "offs";
        }
    },
    Help {
        @Override

        public View getView() {
            return new HelpView();
        }

        @Override
        public String toString() {
            return "help";
        }
    },
    ;

    public abstract View getView();
}

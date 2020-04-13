package View.MainPageLoginAndRegisterView;

import View.AllOffsView.AllOffsView;
import View.AllProductView.AllProductView;
import View.Help.HelpView;
import View.View;
import com.sun.source.tree.BreakTree;

public enum MainPageViewValidCommands {
    CreateAccount {
        public View getView() {
            return new CreateAccount();
        }

        @Override
        public String toString() {
            return "create\\s+account\\s+(buyer|seller|manager)\\s+(.*)";
        }
    },
    LoginAccount {
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
        public String toString() {
            return "back";
        }
    },
    Exit {
        @Override
        public String toString() {
            return "Exit";
        }
    },
    ShowProducts {
        public View getView() {
            return new AllProductView();
        }

        @Override
        public String toString() {
            return "products";
        }
    },
    ShowOffs {
        public View getView() {
            return new AllOffsView();
        }

        @Override
        public String toString() {
            return "offs";
        }
    },
    Help {
        public View getView() {
            return new HelpView();
        }

        @Override
        public String toString() {
            return "help";
        }
    },
}

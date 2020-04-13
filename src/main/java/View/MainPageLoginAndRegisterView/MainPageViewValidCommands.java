package View.MainPageLoginAndRegisterView;

import com.sun.source.tree.BreakTree;

public enum MainPageViewValidCommands {
    CreateAccount {
        @Override
        public String toString() {
            return "create\\s+account\\s+(buyer|seller|manager)\\s+(.*)";
        }
    },
    LoginAccount {
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
        @Override
        public String toString() {
            return "products";
        }
    },
    ShowOffs {
        @Override
        public String toString() {
            return "offs";
        }
    },
    Help {
        @Override
        public String toString() {
            return "help";
        }
    },
}

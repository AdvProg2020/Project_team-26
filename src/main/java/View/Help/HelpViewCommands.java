package View.Help;

import View.View;

import java.util.ArrayList;
import java.util.List;

public enum HelpViewCommands {
    Help {
        public List<String> getTheResultForMainPageLoginAndRegisterView() {
            ArrayList<String> commands = new ArrayList<>();
            commands.add("create account [type] [username]");
            commands.add("login [username]");
            commands.add("Exit");
            commands.add("Back");
            commands.add("Help");
            return commands;
        }
    },
    SortByEspecialField {
        @Override
        public String toString() {
            return "sort\\s+by\\s+(.*)";
        }
    }
}

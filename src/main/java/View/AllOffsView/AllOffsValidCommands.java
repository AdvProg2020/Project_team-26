package View.AllOffsView;

public enum AllOffsValidCommands {
    ShowProductWithId {
        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    }
}

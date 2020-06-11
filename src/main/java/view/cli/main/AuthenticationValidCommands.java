package view.cli.main;

public enum AuthenticationValidCommands {
    CreateAccount("^create\\s+account\\s+(buyer|seller|manager)\\s+(.*)"),
    LoginAccount("^login\\s+(.*)");


    private final String output;

    AuthenticationValidCommands(String output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return this.output;
    }
}

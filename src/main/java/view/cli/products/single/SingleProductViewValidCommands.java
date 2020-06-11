package view.cli.products.single;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SingleProductViewValidCommands {
    AddCommentToThisProduct("add\\s+comment") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.addComment();
        }
    },
    AddTOTHEUserCart("add\\s+to\\s+cart") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.addToTheCart();
        }
    },
    AttributeOfProduct("attributes") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.attributeOfProduct();
        }
    },
    CommentsForThisProduct("Comments") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.commentsForThisProduct();
        }
    },//
    CompareToProductWithId("compare\\s+(\\d+)") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.compareToProductWithId(Pattern.compile(CompareToProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Digest("digest") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.digest();
        }
    },
    Offs("offs") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.off();
        }
    },
    ShowProducts("products") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.product();
        }
    },
    ShowOffs("offs") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.off();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.help();
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(SingleProductView page) {
            if (page.getManager().getIsUserLoggedIn()) {
                page.logOut();
                return;
            }
            page.getManager().inputOutput.println("you are mot logged in");
        }
    },
    CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(SingleProductView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.register();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    },
    LoginAccount("login\\s+(.*)") {
        @Override
        public void goToFunction(SingleProductView page) {
            if (!page.getManager().getIsUserLoggedIn()) {
                page.login();
                return;
            }
            page.getManager().inputOutput.println("first logout");
        }
    };
    private final Pattern commandPattern;
    private final String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }


    public abstract void goToFunction(SingleProductView page);

    SingleProductViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
    }


    @Override
    public String toString() {
        return this.value;
    }
}

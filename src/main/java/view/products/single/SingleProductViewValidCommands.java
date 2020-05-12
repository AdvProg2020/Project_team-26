package view.products.single;

import view.products.all.AllProductView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SingleProductViewValidCommands {
    AddCommentToThisProduct("add\\s+comment") {
        /**
         *
         *
         *
         * @param page
         */
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
    CompareToProductWithId("compare\\s+(.*)") {
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
            //  page.offs();
        }
    },
    ShowProductInOffPage("show\\s+product\\s+(.*)") {
        @Override
        public void goToFunction(SingleProductView page) {
            //   page.showProductInOffPage(Pattern.compile(ShowProductInOffPage.toString()).matcher(page.getInput()));
        }
    },
    ChangeInfo("edit (.*)") {//todo

        @Override
        public void goToFunction(SingleProductView page) {
            page.changeInfo(Pattern.compile(ChangeInfo.toString()).matcher(page.getInput()), true);
        }
    }, Logout("logout") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.logOut();
        }
    }, CreateAccount("create\\s+account\\s+(buyer|seller|manager)\\s+(.*)") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.register();
        }
    },
    LoginAccount("login\\s+(.*)") {
        @Override
        public void goToFunction(SingleProductView page) {
            page.login();
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

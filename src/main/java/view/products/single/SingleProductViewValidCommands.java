package view.products.single;

import view.View;

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
        public void goToFunction(SingleProductViewI page) {
            page.commentsForThisProduct();
        }
    },
    AddTOTHEUserCart("add\\s+to\\s+cart") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.addToTheCart();
        }
    },
    AttributeOfProduct("attributes") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.attributeOfProduct();
        }
    },
    CommentsForThisProduct("Comments") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.commentsForThisProduct();
        }
    },//
    CompareToProductWithId("compare\\s+(.*)") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.compareToProductWithId(Pattern.compile(CompareToProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Digest("digest") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.digest();
        }
    },
    Offs("offs") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.offs();
        }
    },
    ShowProductInOffPage("show\\s+product\\s+(.*)") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.showProductInOffPage(Pattern.compile(ShowProductInOffPage.toString()).matcher(page.getInput()));
        }
    },
    ChangeInfo("") {
        @Override
        public void goToFunction(SingleProductViewI page) {
            page.changeInfo(Pattern.compile(ChangeInfo.toString()).matcher(page.getInput()), true);
        }
    };
    private final Pattern commandPattern;
    private final String value;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }


    public abstract void goToFunction(SingleProductViewI page);

    SingleProductViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        this.value = output;
    }


    @Override
    public String toString() {
        return this.value;
    }
}

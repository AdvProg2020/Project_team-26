package view.products.single;

import view.offs.AllOffsView;
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
        public void goToFunction(SingleProductView page) {
            page.commentsForThisProduct();
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
    CompareToProductWithId("compare\\s+(.*)"){
        @Override
        public void goToFunction(SingleProductView page) {
page.compareToProductWithId(Pattern.compile(CompareToProductWithId.toString()).matcher(page.getInput()));
        }
    },
    Digest("digest"){
        @Override
        public void goToFunction(SingleProductView page) {
            page.digest();
        }
    },
    Offs("offs"){
        @Override
        public void goToFunction(SingleProductView page) {
            page.offs();
        }
    },
    SelectAUserForBuyingFrom("select\\s+seller\\s+(.*)"){
        @Override
        public void goToFunction(SingleProductView page) {
            page.selectAUserForBuyingFrom(Pattern.compile(SelectAUserForBuyingFrom.toString()).matcher(page.getInput()));
        }
    },
    ShowProductInOffPage("show\\s+product\\s+(.*)"){
        @Override
        public void goToFunction(SingleProductView page) {
            page.showProductInOffPage(Pattern.compile(ShowProductInOffPage.toString()).matcher(page.getInput()));
        }
    },
    ChangeInfo(""){
        @Override
        public void goToFunction(SingleProductView page) {
            page.changeInfo(Pattern.compile(ChangeInfo.toString()).matcher(page.getInput()),true);
        }
    };
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(SingleProductView page);

    SingleProductViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }


}

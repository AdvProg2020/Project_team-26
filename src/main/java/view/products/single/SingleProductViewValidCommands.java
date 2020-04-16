package view.products.single;

import view.offs.AllOffsView;
import view.View;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SingleProductViewValidCommands {
    AddCommentToThisProduct("add\\s+comment"),
    AddTOTHEUserCart("add\\s+to\\s+cart"),
    AttributeOfProduct("attributes"),
    CommentsForThisProduct("Comments"),
    CompareToProductWithId("compare\\s+(.*)"),
    Digest("digest"),
    Offs("offs"),
    SelectAUserForBuyingFrom("select\\s+seller\\s+(.*)"),
    ShowProductInOffPage("show\\s+product\\s+(.*)"),
    ChangeInfo("");
    private final Pattern commandPattern;

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    SingleProductViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
    }


}

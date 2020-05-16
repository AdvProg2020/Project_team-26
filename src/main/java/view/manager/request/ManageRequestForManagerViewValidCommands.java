package view.manager.request;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ManageRequestForManagerViewValidCommands {
    ShowAllOff("show\\s+offs") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.showAllOff();
        }
    }, ShowAllProduct("show\\s+products") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.showAllProduct();
        }
    }, ShowAllProductSeller("show\\s+sellers") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.showAllProductSeller();
        }
    },
    DetailOfOffRequest("details\\s+\\s+off\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.detailTheOffRequest(Pattern.compile(DetailOfOffRequest.toString()).matcher(page.getInput()));
        }
    },
    DetailOfProductRequest("details\\s+\\s+products\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.detailTheProductRequest(Pattern.compile(DetailOfProductRequest.toString()).matcher(page.getInput()));
        }
    }, DetailOfProductSellerRequest("details\\s+\\s+product\\s+seller\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.declineTheProductSellerRequest(Pattern.compile(DetailOfProductSellerRequest.toString()).matcher(page.getInput()));
        }
    },

    AcceptTheOffRequest("accept\\s+\\s+off\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.acceptTheOffRequest(Pattern.compile(AcceptTheOffRequest.toString()).matcher(page.getInput()));
        }
    }, AcceptTheProductRequest("accept\\s+\\s+product\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.acceptTheProductRequest(Pattern.compile(AcceptTheProductRequest.toString()).matcher(page.getInput()));
        }
    }, AcceptTheProductSellerRequest("accept\\s+\\s+product\\s+seller\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.acceptTheProductSellerRequest(Pattern.compile(AcceptTheProductSellerRequest.toString()).matcher(page.getInput()));
        }
    },

    DeclineTheOffRequest("decline\\s+\\s+off\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.declineTheOffRequest(Pattern.compile(DeclineTheOffRequest.toString()).matcher(page.getInput()));
        }
    }, DeclineTheProductRequest("decline\\s+\\s+product\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.declineTheProductRequest(Pattern.compile(DeclineTheProductRequest.toString()).matcher(page.getInput()));
        }
    }, DeclineTheProductSellerRequest("decline\\s+\\s+product\\s+seller\\s+(\\d+)") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
            page.declineTheProductSellerRequest(Pattern.compile(DeclineTheProductSellerRequest.toString()).matcher(page.getInput()));
        }
    },
    Logout("logout") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
           page.logOut();
        }
    },
    Help("help") {
        @Override
        public void goToFunction(ManageRequestForManagerView page) {
             page.help();
        }
    };
    private final Pattern commandPattern;
    private final String value;
    @Override
    public String toString() {
        return value;
    }

    public Matcher getStringMatcher(String input) {
        return this.commandPattern.matcher(input);

    }

    public abstract void goToFunction(ManageRequestForManagerView page);

    ManageRequestForManagerViewValidCommands(String output) {
        this.commandPattern = Pattern.compile(output);
        value = output;
    }


}

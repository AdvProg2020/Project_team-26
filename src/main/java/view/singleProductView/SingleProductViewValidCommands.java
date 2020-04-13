package view.singleProductView;

import view.allOffsView.AllOffsView;
import view.View;

public enum SingleProductViewValidCommands {
    AddTOTHEUserCart {
        @Override
        public View getView() {
            return new AddToTheUserCart();
        }

        @Override
        public String toString() {
            return "add\\s+to\\s+cart";
        }
    },
    SelectAUserForBuyingFrom {
        @Override
        public View getView() {
            return new SelectAUserForBuyingFrom();
        }

        @Override
        public String toString() {
            return "select\\s+seller\\s+(.*)";
        }
    },
    AttributeOfProduct {
        @Override
        public View getView() {
            return new AttributeOfProduct();
        }

        @Override
        public String toString() {
            return "attributes";
        }
    },
    CompareToProductWithId {
        @Override
        public View getView() {
            return new CompareToProductWithId();
        }

        @Override
        public String toString() {
            return "compare\\s+(.*)";
        }
    },
    CommentsForThisProduct {
        @Override
        public View getView() {
            return new CommentsForThisProduct();
        }

        @Override
        public String toString() {
            return "Comments";
        }
    },
    AddCommentToThisProduct {
        @Override
        public View getView() {
            return new AddCommentToThisProduct();
        }

        @Override
        public String toString() {
            return "add\\s+comment";
        }
    },
    Offs {
        @Override
        public View getView() {
            return new AllOffsView();
        }

        @Override
        public String toString() {
            return "offs";
        }
    },
    ShowProductInOffPage {
        @Override
        public View getView() {
            return new OffsForThisProduct();
        }

        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    },
    Digest {
        @Override
        public View getView() {
            return new Digest();
        }

        @Override
        public String toString() {
            return "digest";
        }
    };

    public abstract View getView();


}

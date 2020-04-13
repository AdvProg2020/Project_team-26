package View.SingleProductView;

import View.AllOffsView.AllOffsView;
import View.View;

public enum SingleProductViewValidCommands {
    AddTOTHEUserCart {
        public View getView(){
            return new AddToTheUserCart();
        }
        @Override
        public String toString() {
            return "add\\s+to\\s+cart";
        }
    },
    SelectAUserForBuyingFrom {
        public View getView(){
            return new SelectAUserForBuyingFrom();
        }
        @Override
        public String toString() {
            return "select\\s+seller\\s+(.*)";
        }
    },
    AttributeOfProduct {
        public View getView(){
            return new AttributeOfProduct();
        }
        @Override
        public String toString() {
            return "attributes";
        }
    },
    CompareToProductWithId {
        public View getView(){
            return new CompareToProductWithId();
        }
        @Override
        public String toString() {
            return "compare\\s+(.*)";
        }
    },
    CommentsForThisProduct {
        public View getView(){
            return new CommentsForThisProduct();
        }
        @Override
        public String toString() {
            return "Comments";
        }
    },
    AddCommentToThisProduct {
        public View getView(){
            return new AddCommentToThisProduct();
        }
        @Override
        public String toString() {
            return "add\\s+comment";
        }
    },
    Offs {
        public View getView() {
            return new AllOffsView();
        }
        @Override
        public String toString() {
            return "offs";
        }
    },
    ShowProductInOffPage {
        public View getView() {
            return new OffsForThisProduct();
        }

        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    },
    Digest {
        public View getView() {
            return new Digest();
        }

        @Override
        public String toString() {
            return "digest";
        }
    }


}

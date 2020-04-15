package view.products.single;

import view.offs.AllOffsView;
import view.View;

public enum SingleProductViewValidCommands {
    AddTOTHEUserCart {
        @Override
        public View getView() {
            return new AddToCart();
        }

        @Override
        public String toString() {
            return "add\\s+to\\s+cart";
        }
    },
    SelectAUserForBuyingFrom {
        @Override
        public View getView() {
            return new SelectSeller();
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
            return new CompareTo();
        }

        @Override
        public String toString() {
            return "compare\\s+(.*)";
        }
    },
    CommentsForThisProduct {
        @Override
        public View getView() {
            return new Comments();
        }

        @Override
        public String toString() {
            return "Comments";
        }
    },
    AddCommentToThisProduct {
        @Override
        public View getView() {
            return new AddComment();
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
            return new Offs();
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

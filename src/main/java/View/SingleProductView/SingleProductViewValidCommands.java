package View.SingleProductView;

public enum SingleProductViewValidCommands {
    AddTOTHEUserCart {
        @Override
        public String toString() {
            return "add\\s+to\\s+cart";
        }
    },
    SelectAUserForBuyingFrom {
        @Override
        public String toString() {
            return "select\\s+seller\\s+(.*)";
        }
    },
    AttributeOfProduct {
        @Override
        public String toString() {
            return "attributes";
        }
    },
    CompareToProductWithId {
        @Override
        public String toString() {
            return "compare\\s+(.*)";
        }
    },
    CommentsForThisProduct {
        @Override
        public String toString() {
            return "Comments";
        }
    },
    AddCommentToThisProduct {
        @Override
        public String toString() {
            return "add\\s+comment";
        }
    },
    Offs {
        @Override
        public String toString() {
            return "offs";
        }
    },
    ShowProductInOffPage {
        @Override
        public String toString() {
            return "show\\s+product\\s+(.*)";
        }
    }
}

package View.SellerAccountView.ManageProductForSeller;

public enum ManageProductForSellerViewValidCommands {
    ViewProductWithId {
        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },

    ViewBuyersOfProductWithId {
        @Override
        public String toString() {
            return "view\\s+buyers\\s+(.*)";
        }
    },
    EditProductWithId {
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },
}

package View.SellerAccountView;

public enum SellerAccountViewValidCommands {

    ViewPersonalInfo {
        @Override
        public String toString() {
            return "view\\s+personal\\s+info";
        }
    },
    EditTheFiled {
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },

    ViewCompanyInfo {
        @Override
        public String toString() {
            return "view\\s+company\\s+information";
        }
    },

    ViewSalesHistoryForSeller {
        @Override
        public String toString() {
            return "view\\s+sales\\s+history";
        }
    },

    ManageProduct {
        @Override
        public String toString() {
            return "manage\\s+products";
        }
    },
    AddProductForSeller {
        @Override
        public String toString() {
            return "add\\s+product";
        }
    },
    RemoveProductWithProductId {
        @Override
        public String toString() {
            return "remove\\s+product\\s+productId";
        }
    },
    ShowAllCategoriesForSeller {
        @Override
        public String toString() {
            return "show\\s+categories";
        }
    },
    ViewAllOffsForSeller {
        @Override
        public String toString() {
            return "view\\s+offs";
        }
    },
    ViewBalanceForSeller {
        @Override
        public String toString() {
            return "view\\s+balance";
        }
    }

}

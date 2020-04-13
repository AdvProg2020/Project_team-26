package View.SellerAccountView.ViewOffsForSellerAccount;

public enum ViewOffsForSellerAccountValidCommands {
    ViewOffWithIdForSeller {
        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },
    EditOffWithIdForSeller {
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },
    AddOffForSeller {
        @Override
        public String toString() {
            return "add\\s+off";
        }
    },
}

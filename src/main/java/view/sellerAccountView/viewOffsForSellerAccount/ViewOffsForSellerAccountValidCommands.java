package view.sellerAccountView.viewOffsForSellerAccount;

import view.View;

public enum ViewOffsForSellerAccountValidCommands {
    ViewOffWithIdForSeller {
        @Override
        public View getView() {
            return new ViewOffsForSellerAccount();
        }

        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },
    EditOffWithIdForSeller {
        @Override
        public View getView() {
            return new EditOffWithIdForSeller();
        }
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },
    AddOffForSeller {
        @Override
        public View getView() {
            return new AddOffForSeller();
        }
        @Override
        public String toString() {
            return "add\\s+off";
        }
    };

    public abstract View getView();
}

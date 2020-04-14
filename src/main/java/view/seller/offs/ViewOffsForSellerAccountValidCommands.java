package view.seller.offs;

import view.View;

public enum ViewOffsForSellerAccountValidCommands {
    ViewOffWithIdForSeller {
        @Override
        public View getView() {
            return new ShowAllOffs();
        }

        @Override
        public String toString() {
            return "view\\s+(.*)";
        }
    },
    EditOffWithIdForSeller {
        @Override
        public View getView() {
            return new EditOff();
        }
        @Override
        public String toString() {
            return "edit\\s+(.*)";
        }
    },
    AddOffForSeller {
        @Override
        public View getView() {
            return new AddOff();
        }
        @Override
        public String toString() {
            return "add\\s+off";
        }
    };

    public abstract View getView();
}

package View.BuyeyAccountView;

public enum BuyerAccountViewValidCommands {
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

    ViewCart {
        @Override
        public String toString() {
            return "view\\s+cart";
        }
    },

    ViewOrdersForBuyer {
        @Override
        public String toString() {
            return "view\\s+orders";
        }
    },
    ViewBalanceToBuyer {
        @Override
        public String toString() {
            return "view\\s+balance";
        }
    },
    ViewDiscountCodesToBuyer {
        @Override
        public String toString() {
            return "view\\s+discount\\s+codes";
        }
    }
}

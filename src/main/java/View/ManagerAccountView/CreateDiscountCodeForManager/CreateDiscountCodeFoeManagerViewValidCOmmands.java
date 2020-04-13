package View.ManagerAccountView.CreateDiscountCodeForManager;

public enum CreateDiscountCodeFoeManagerViewValidCOmmands {
    ViewDiscountCodeWithItsCode {
        @Override
        public String toString() {
            return "view\\s+discount\\+code\\+(.*)";
        }
    },
    EditDiscountCodeWithItsCode {
        @Override
        public String toString() {
            return "edit\\s+discount\\+code\\+(.*)";
        }
    },
    RemoveDiscountCodeWithItsCode {
        @Override
        public String toString() {
            return "remove\\s+discount\\s+code\\s+(.*)";
        }
    },

}

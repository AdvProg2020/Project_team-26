package view.manager.discount;

import view.View;

public enum CreateDiscountCodeFoeManagerViewValidCOmmands {
    ViewDiscountCodeWithItsCode {
        @Override
        public View getView() {

            return new ViewDiscountCodeWithItsCode();
        }

        @Override
        public String toString() {
            return "view\\s+discount\\+code\\+(.*)";
        }
    },
    EditDiscountCodeWithItsCode {
        @Override
        public View getView() {
            return new EditDiscountCodeWithItsCode();
        }

        @Override
        public String toString() {
            return "edit\\s+discount\\+code\\+(.*)";
        }
    },
    RemoveDiscountCodeWithItsCode {
        @Override
        public View getView() {
            return new RemoveDiscountCodeWithItsCode();
        }

        @Override
        public String toString() {
            return "remove\\s+discount\\s+code\\s+(.*)";
        }
    },
    ;

    public abstract View getView();

}

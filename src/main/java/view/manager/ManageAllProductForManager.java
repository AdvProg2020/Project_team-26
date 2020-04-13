package view.manager;

import view.*;

public class ManageAllProductForManager extends View {
    private RemoveProductInManageProduct remove;

    @Override
    public View run(ViewManager manager) {
        return null;
    }

    enum RemoveProductInManageProduct {
        RemoveProductInManageProduct {
            @Override
            public void removeProduct(String productId) {

            }

            @Override
            public String toString() {
                return "remove\\s+(.*)";
            }

        };

        public abstract void removeProduct(String productId);
    }
}

package view.gui;

import model.ProductSeller;

import java.io.IOException;

public class SingleProductOfCartController implements InitializableController {
    private ProductSeller productSeller;
    private int amount;

    @Override
    public void initialize(int id) throws IOException {

    }

    public void load(ProductSeller productSeller, int amount) {
        this.productSeller = productSeller;
        this.amount = amount;
    }
}

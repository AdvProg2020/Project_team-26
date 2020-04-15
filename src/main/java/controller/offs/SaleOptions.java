package controller.offs;

import model.Off;

public class SaleOptions {

        private static SaleOptions singletonInstance;

        private SaleOptions() {

        }

        public static SaleOptions getInstance() {
                if(singletonInstance == null) {
                        return new SaleOptions();
                }else {
                        return singletonInstance;
                }
        }

        public void createANewSale(Off sale) {

        }

        public void editASale(Off oldSale, Off newSale) {

        }

        public void removeASale(Off sale) {

        }
}

package view.gui.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import model.Promo;
import model.User;
import view.gui.InitializableController;

import java.io.IOException;
import java.util.*;

public class PromoCodesForCustomer implements InitializableController {
    private int id;
    @FXML
    private TableView tableView;
    private TableColumn<Date, PromoPaper> startDate;
    @FXML
    private TableColumn<Date, PromoPaper> endDate;
    @FXML
    private TableColumn<String, PromoPaper> code;
    @FXML
    private TableColumn<Double, PromoPaper> percent;
    @FXML
    private TableColumn<Long, PromoPaper> max;
    @FXML
    private TableColumn<Integer, PromoPaper> maxValidUse;


    @Override
    public void initialize(int id) throws IOException {
        this.id = id;

    }

    public void load(List<Promo> promoList) {
        ArrayList<PromoPaper> promoPaperArrayList = new ArrayList<>();
        promoList.forEach(i -> promoPaperArrayList.add(new PromoPaper(i.getStartDate(), i.getEndDate(), i.getPromoCode(), i.getPercent(), i.getMaxDiscount(), i.getMaxValidUse(), i.getId())));
        ObservableList<PromoPaper> observableList = FXCollections.observableList(promoPaperArrayList);
        loadTable(observableList);
    }

    private void loadTable(ObservableList<PromoPaper> observableList) {
        startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        code.setCellValueFactory(new PropertyValueFactory<>("code"));
        percent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        max.setCellValueFactory(new PropertyValueFactory<>("max"));
        maxValidUse.setCellValueFactory(new PropertyValueFactory<>("maxValidUse"));
        tableView.setItems(observableList);
        tableView.setEditable(false);
    }


    private class PromoPaper {
        int id;
        private Date startDate;
        private Date endDate;
        private String code;
        private Double percent;
        private Long max;
        private Integer maxValidUse;

        public PromoPaper(Date startDate, Date endDate, String code, Double percent, Long max, Integer maxValidUse, int id) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.code = code;
            this.percent = percent;
            this.max = max;
            this.maxValidUse = maxValidUse;
            this.id = id;
        }

        public Date getStartDate() {
            return startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public String getCode() {
            return code;
        }

        public Double getPercent() {
            return percent;
        }

        public Long getMax() {
            return max;
        }

        public Integer getMaxValidUse() {
            return maxValidUse;
        }
    }
}

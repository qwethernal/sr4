package menu;

import CustomerAddBalance.CustomerAddBalanceController;
import CustomerCreate.CustomerCreationController;
import CustomerEdit.CustomerEditController;
import CustomerList.CustomerListController;
import ProductCreate.ProductCreationController;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuScreenController {

    @FXML
    private MenuItem miAddProduct;

    @FXML
    private MenuItem miCheckProducts;

    @FXML
    private MenuItem miNewUser;

    @FXML
    private MenuItem miEditProduct;
    
    @FXML
    private Label discountLabel;

    public void setDiscountStartDate(LocalDate discountStartDate) {
        this.discountStartDate = discountStartDate;
        updateDiscountLabel();
    }

    private void updateDiscountLabel() {
        LocalDate currentDate = LocalDate.now();
        if (discountStartDate != null) {
            if (discountStartDate.equals(currentDate)) {
                discountLabel.setText("Скидка уже началась!");
            } else {
                discountLabel.setText("Скидки начнутся с " + discountStartDate);
            }
        } else {
            discountLabel.setText("Скидок пока нет.");
        }
    }
    @FXML
    private MenuItem miListUsers;
    
    @FXML
    private MenuItem ratingC;
    
    @FXML
    private MenuItem ratingP;

    @FXML
    private MenuItem miProfile;

    @FXML
    private MenuItem miAddCustomer;

    @FXML
    private MenuItem miEditCustomer;
    
    @FXML
    private MenuItem miAddMoney;

    @FXML
    private MenuItem miListCustomers;
    
    @FXML
    private MenuItem Revenue;
        
    private LocalDate discountStartDate;

    
    @FXML
    public void addNewProduct() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductCreate/ProductCreation.fxml"));
            Parent root = loader.load();
            ProductCreationController controller = loader.getController();
            controller.setDiscountStartDate(discountStartDate);
            Stage stage = new Stage();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Добавление продукта");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void RatingCOpen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RatingCustomer/ratingC.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Рейтинг покупателей");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void RatingPOpen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RatingProduct/ratingP.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Рейтинг продуктов");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void RevenueOpen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Revenue/Revenue.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Оборот магазина");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   @FXML
    public void addMoney() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerAddBalance/CustomerAddBalance.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Пополнение баланса");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Transaction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Transaction/TransactionScreen.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Покупка");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void checkProducts(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductList/ProductList.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Список продуктов");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductEdit/ProductEdit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Редактирование продукта");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addNewCustomer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerCreate/CustomerCreation.fxml"));
            Parent root = loader.load();
            CustomerCreationController controller = loader.getController();
            Stage stage = new Stage();
            controller.setStage(stage);
            stage.setScene(new Scene(root));
            stage.setTitle("Добавление покупателя");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void editCustomer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerEdit/CustomerEdit.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Редактирование покупателя");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void listCustomers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerList/CustomerList.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Список покупателей");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

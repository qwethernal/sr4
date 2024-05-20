package ProductEdit;

import entity.Product;
import java.util.HashSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ProductEditController {

    @FXML
    private ListView<Product> productListView;

    @FXML
    private TextField tfNewTitle;

    @FXML
    private TextField tfNewPrice;

    @FXML
    private TextField tfNewAmount;
        
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        loadProductList();
    }

    private void loadProductList() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        EntityManager em = emf.createEntityManager();

        List<Product> products = em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        productList.addAll(products);
        productListView.setItems(productList);

        em.close();
        emf.close();
    }

    @FXML
    void clickEditProduct() {
        Product selectedProduct = productListView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            showAlert("Выберите клиента для редактирования.");
            return;
        }

        String newName = tfNewTitle.getText();
        String newPriceStr = tfNewPrice.getText();
        String newAmountStr = tfNewAmount.getText();

        if (newName.isEmpty() || newPriceStr.isEmpty() || newAmountStr.isEmpty()) {
            showAlert("Введите корректное имя, баланс или количество.");
            return;
        }

        try {
            Double newPrice = Double.parseDouble(newPriceStr);
            Long newAmount = Long.parseLong(newAmountStr);

            selectedProduct.setTitle(newName);
            selectedProduct.setPrice(newPrice);
            selectedProduct.setAmount(newAmount);
            
            

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            em.merge(selectedProduct);
            em.getTransaction().commit();

            em.close();
            emf.close();

            showAlert("Продукт успешно обновлен.");
            closeWindow();
        } catch (NumberFormatException ex) {
            showAlert("Введите корректный баланс или количество.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) productListView.getScene().getWindow();
        stage.close();
    }
}

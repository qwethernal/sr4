package CustomerEdit;

import entity.Customer;
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

public class CustomerEditController {

    @FXML
    private ListView<Customer> customerListView;

    @FXML
    private TextField tfNewName;

    @FXML
    private TextField tfNewBalance;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        loadCustomerList();
    }

    private void loadCustomerList() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        EntityManager em = emf.createEntityManager();

        List<Customer> customers = em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
        customerList.addAll(customers);
        customerListView.setItems(customerList);

        em.close();
        emf.close();
    }

    @FXML
    void clickEditCustomer() {
        Customer selectedCustomer = customerListView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("Выберите клиента для редактирования.");
            return;
        }

        String newName = tfNewName.getText();
        String newBalanceStr = tfNewBalance.getText();

        if (newName.isEmpty() || newBalanceStr.isEmpty()) {
            showAlert("Введите новое имя и баланс.");
            return;
        }

        try {
            Double newBalance = Double.parseDouble(newBalanceStr);

            selectedCustomer.setName(newName);
            selectedCustomer.setBalance(newBalance);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();
            em.merge(selectedCustomer);
            em.getTransaction().commit();

            em.close();
            emf.close();

            showAlert("Клиент успешно обновлен.");
            closeWindow();
        } catch (NumberFormatException ex) {
            showAlert("Введите корректный баланс.");
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
        Stage stage = (Stage) customerListView.getScene().getWindow();
        stage.close();
    }
}

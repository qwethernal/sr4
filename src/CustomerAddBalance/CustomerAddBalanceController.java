/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CustomerAddBalance;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entity.Customer;
import entity.Product;
import javafx.stage.Stage;
import menu.MenuScreenController;

public class CustomerAddBalanceController {

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private TextField quantityTextField;
    

    private EntityManagerFactory emf;

    private MenuScreenController parentController;

    public void setParentController(MenuScreenController parentController) {
        this.parentController = parentController;
    }
    @FXML
    void initialize() {
        // Инициализация ComboBox'ов и других элементов управления
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        loadCustomers();
    }

    private void loadCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            customerComboBox.getItems().addAll(em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList());
        } finally {
            em.close();
        }
    }

    @FXML
    void addingMoney() {
        Customer selectedCustomer = customerComboBox.getValue();
        double quantityToBuy = Double.parseDouble(quantityTextField.getText());

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            selectedCustomer = em.find(Customer.class, selectedCustomer.getId());

            if (selectedCustomer == null) {
                showAlert("Покупатель не найден");
                return;
            }
            Double CustomerBalance = selectedCustomer.getBalance();
            selectedCustomer.setBalance(CustomerBalance + quantityToBuy);
            em.getTransaction().commit();
            showAlert("Баланс успешно пополнен!");
            Scene scene = quantityTextField.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        } catch (Exception e) {
            em.getTransaction().rollback();
            showAlert("Ошибка: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

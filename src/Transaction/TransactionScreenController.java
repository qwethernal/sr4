/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Transaction;

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
import entity.Transaction;
import javafx.stage.Stage;
import menu.MenuScreenController;

public class TransactionScreenController {

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<Product> productComboBox;

    @FXML
    private TextField quantityTextField;

    private EntityManagerFactory emf;

    private MenuScreenController parentController;

    public void setParentController(MenuScreenController parentController) {
        this.parentController = parentController;
    }

    @FXML
    void initialize() {
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        loadCustomers();
        loadProducts();
    }

    private void loadCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            customerComboBox.getItems().addAll(em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList());
        } finally {
            em.close();
        }
    }

    private void loadProducts() {
        EntityManager em = emf.createEntityManager();
        try {
            productComboBox.getItems().addAll(em.createQuery("SELECT p FROM Product p", Product.class).getResultList());
        } finally {
            em.close();
        }
    }

    @FXML
    void performTransaction() {
        Customer selectedCustomer = customerComboBox.getValue();
        Product selectedProduct = productComboBox.getValue();
        long quantityToBuy = Long.parseLong(quantityTextField.getText());

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            selectedCustomer = em.find(Customer.class, selectedCustomer.getId());
            selectedProduct = em.find(Product.class, selectedProduct.getId());

            if (selectedCustomer == null) {
                showAlert("Customer not found");
                return;
            }

            if (selectedProduct == null) {
                showAlert("Product not found");
                return;
            }

            if (quantityToBuy > selectedProduct.getAmount()) {
                showAlert("Not enough quantity of the product");
                return;
            }

            double totalCost = selectedProduct.getPrice() * quantityToBuy;
            if (totalCost > selectedCustomer.getBalance()) {
                showAlert("Not enough balance");
                return;
            }

            selectedCustomer.setBalance(selectedCustomer.getBalance() - totalCost);
            selectedProduct.setAmount(selectedProduct.getAmount() - quantityToBuy);
            Transaction transaction = new Transaction();
            transaction.setAmount(totalCost);
            transaction.setQuantity(quantityToBuy);
            transaction.setCustomer(selectedCustomer);
            transaction.setProduct(selectedProduct);
            em.persist(transaction);

            em.getTransaction().commit();
            showAlert("Transaction completed successfully");
            Scene scene = quantityTextField.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
        } catch (Exception e) {
            em.getTransaction().rollback();
            showAlert("Transaction failed: " + e.getMessage());
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

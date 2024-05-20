package CustomerCreate;

import entity.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class CustomerCreationController {

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfBalance;

    private Stage stage;

    private EntityManagerFactory emf;
    private EntityManager em;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void initialize() {
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        em = emf.createEntityManager();
    }

    @FXML
    void clickAddCustomer() {
        String name = tfName.getText();
        Double balance = Double.parseDouble(tfBalance.getText());

        try {
            Customer existingCustomer = em.createQuery("SELECT c FROM Customer c WHERE c.name = :name", Customer.class)
                                         .setParameter("name", name)
                                         .getSingleResult();

            showAlert("Клиент с таким именем уже существует.");
        } catch (Exception ex) {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setBalance(balance);

            em.getTransaction().begin();
            em.persist(customer);
            em.getTransaction().commit();

            showAlert("Клиент успешно добавлен.");
            stage.close();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    void closeEntityManager() {
        em.close();
        emf.close();
    }
}

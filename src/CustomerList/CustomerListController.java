package CustomerList;

import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class CustomerListController {

    @FXML
    private ListView<Customer> customerListView;

    private EntityManagerFactory emf;

    @FXML
    void initialize() {
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        loadAndDisplayCustomers();
    }

    private void loadAndDisplayCustomers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c", Customer.class);
            ObservableList<Customer> customers = FXCollections.observableArrayList(query.getResultList());
            customerListView.setItems(customers);

            customerListView.setCellFactory(param -> new TextFieldListCell<Customer>() {
                @Override
                public void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getName() + " - Баланс: " + item.getBalance() + "€" );
                    }
                }
            });
        } finally {
            em.close();
        }
    }
}

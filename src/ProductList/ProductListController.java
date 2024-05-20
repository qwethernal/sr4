package ProductList;

import entity.Product;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductListController {

    @FXML
    private ListView<Product> productListView;

    private EntityManagerFactory emf;

    @FXML
    void initialize() {
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        loadAndDisplayProducts();
    }

    private void loadAndDisplayProducts() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
            ObservableList<Product> products = FXCollections.observableArrayList(query.getResultList());
            productListView.setItems(products);

            productListView.setCellFactory(param -> new TextFieldListCell<Product>() {
                @Override
                public void updateItem(Product item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getTitle() + " - Цена: " + item.getPrice() + "€" + ", Количество: " + item.getAmount());
                    }
                }
            });
        } finally {
            em.close();
        }
    }
}

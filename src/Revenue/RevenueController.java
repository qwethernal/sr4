package Revenue;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.net.URL;
import java.util.ResourceBundle;

public class RevenueController implements Initializable {

    @FXML
    private Label label123;

    private EntityManagerFactory emf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        loadRevenue();
    }

    private void loadRevenue() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpqlTotal = "SELECT SUM(t.amount) FROM Transaction t";
            TypedQuery<Double> queryTotal = em.createQuery(jpqlTotal, Double.class);
            Double totalAmount = queryTotal.getSingleResult();
            if (totalAmount != null) {
            label123.setText("Оборот магазина: " + totalAmount + "€");
                } else {
            label123.setText("Транзакций еще не было");
                }
        } finally {
            em.close();
        }
    }
}

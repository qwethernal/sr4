package RatingCustomer;

import entity.Customer;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javafx.scene.control.Label;


public class RatingCController {
    private LocalDate currentDate = LocalDate.now();

    @FXML
    private ListView<String> customerListView;

    private EntityManagerFactory emf;
    
    @FXML
    private Label nameRating;

    @FXML
    void initialize() {
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        RatingList();
    }

    private void RatingList() {
        nameRating.setText("Рейтинг покупателей на момент " + currentDate );
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT t.customer.id, t.customer.name, SUM(t.amount) FROM Transaction t GROUP BY t.customer ORDER BY SUM(t.amount) DESC", 
                Object[].class
            );
            List<Object[]> rankingResults = query.getResultList();

            ObservableList<String> ratingList = FXCollections.observableArrayList();
            int rank = 1;
            for (Object[] result : rankingResults) {
                String name = (String) result[1];
                double totalSpent = (double) result[2];

                // Добавить информацию о покупателе и потраченных деньгах в список рейтинга
                ratingList.add(rank + "." + name + " - Потрачено: " + totalSpent + "€");
                rank++;
            }

            customerListView.setItems(ratingList);

        } finally {
            em.close();
        }
    } 
}
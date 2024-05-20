/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RatingProduct;


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


public class RatingPController {
    private LocalDate currentDate = LocalDate.now();

    @FXML
    private ListView<String> productListView;

    private EntityManagerFactory emf;
    
    @FXML
    private Label nameRating;

    @FXML
    void initialize() {
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        RatingList();
    }

    private void RatingList() {
        nameRating.setText("Рейтинг продуктов на момент " + currentDate );
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT t.product.id, t.product.title, SUM(t.quantity) FROM Transaction t GROUP BY t.product ORDER BY SUM(t.quantity) DESC", 
                Object[].class
            );
            List<Object[]> rankingResults = query.getResultList();

            ObservableList<String> ratingList = FXCollections.observableArrayList();
            int rank = 1;
            for (Object[] result : rankingResults) {
                String name = (String) result[1];
                long quantity = (long) result[2];

                ratingList.add(rank + "." + name + " - Куплено: " + quantity + " раз");
                rank++;
            }

            productListView.setItems(ratingList);

        } finally {
            em.close();
        }
    } 
}
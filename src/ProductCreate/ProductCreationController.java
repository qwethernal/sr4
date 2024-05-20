package ProductCreate;

import entity.Product;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class ProductCreationController {

    @FXML
    private TextField tfTitle;

    @FXML
    private TextField tfPrice;

    @FXML
    private TextField tfQuantity;
    
    private LocalDate discountStartDate;

    @FXML
    private Button btAddBook;
    
    // Добавляем поле для объекта Stage
    private Stage stage;

    // Выносим EntityManager и EntityManagerFactory в поля класса для повторного использования
    private EntityManagerFactory emf;
    private EntityManager em;

    // Добавляем метод для установки Stage
    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    void initialize() {
        // Инициализируем EntityManagerFactory один раз при запуске приложения
        emf = Persistence.createEntityManagerFactory("FXlibrarybasePU");
        em = emf.createEntityManager();
    }

    @FXML
    void clickAddBook(ActionEvent event) {
        String title = tfTitle.getText();
        Double price = Double.parseDouble(tfPrice.getText());
        Long amount = Long.parseLong(tfQuantity.getText());

        try {
            // Проверяем, существует ли продукт с таким же именем
            Product existingProduct = em.createQuery("SELECT p FROM Product p WHERE p.title = :title", Product.class)
                                         .setParameter("title", title)
                                         .getSingleResult();

            // Если продукт с таким именем уже существует, выводим сообщение об ошибке
            showAlert("Продукт с таким именем уже существует в базе данных.");
        } catch (NoResultException ex) {
            // Если продукт с таким именем не существует, добавляем новый продукт
            Product product = new Product();
            product.setTitle(title);
            if (LocalDate.now().isEqual(discountStartDate)) {
                // Применяем скидку, если сегодня - день скидок
                product.setPrice(price * 0.5); // Цена на 50% дешевле
            } else {
                product.setPrice(price);
            }
            product.setAmount(amount);

            em.getTransaction().begin(); // Начинаем транзакцию перед сохранением продукта
            em.persist(product);
            em.getTransaction().commit(); // Фиксируем транзакцию после успешного сохранения

            showAlert("Продукт успешно добавлен в базу данных.");
            // Закрываем окно после добавления продукта
            this.stage.close();

        } catch (Exception ex) {
            showAlert("Ошибка при добавлении продукта: " + ex.getMessage());
        }
    }
    
    public void setDiscountStartDate(LocalDate discountStartDate) {
        this.discountStartDate = discountStartDate;
        updateDiscountLabel();
    }


    void closeEntityManager() {
        em.close();
        emf.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Сообщение");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void updateDiscountLabel() {
    }
    
}

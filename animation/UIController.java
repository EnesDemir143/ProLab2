import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UIController implements Initializable {
    @FXML private ImageView computerCard1, computerCard2, computerCard3, computerCard4, computerCard5, computerCard6;
    @FXML private ImageView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6;
    @FXML private ImageView computerSelectedCard1, computerSelectedCard2, computerSelectedCard3;
    @FXML private ImageView playerSelectedCard1, playerSelectedCard2, playerSelectedCard3;
    @FXML private Button finishButton;

    // Yeni eklenen göstergeler
    @FXML private StackPane playerCard1Indicator, playerCard2Indicator, playerCard3Indicator;
    @FXML private Label playerCard1HealthLabel, playerCard2HealthLabel, playerCard3HealthLabel;
    @FXML private Label playerCard1LevelLabel, playerCard2LevelLabel, playerCard3LevelLabel;
    @FXML private Circle playerCard1HealthCircle, playerCard2HealthCircle, playerCard3HealthCircle, playerCard4HealthCircle ,playerCard5HealthCircle, playerCard6HealthCircle ;

    private Map<ImageView, ImageView> selectedToOriginalMap = new HashMap<>();
    private int selectedCardCount = 0;

    // Can ve seviye değerleri
    private int playerCard1Health = 100, playerCard2Health = 100, playerCard3Health = 100;
    private int playerCard1Level = 1, playerCard2Level = 1, playerCard3Level = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCardImages();
        setupCardClickEvents();
        setupHealthIndicators();
    }

    private void loadCardImages() {
        try {
            String basePath = "C:/Allcodes/prolab2/";

            // Bilgisayar kartları
            computerCard1.setImage(new Image(new File(basePath + "arka1.png").toURI().toString()));
            computerCard2.setImage(new Image(new File(basePath + "arka1.png").toURI().toString()));
            computerCard3.setImage(new Image(new File(basePath + "arka1.png").toURI().toString()));
            computerCard4.setImage(new Image(new File(basePath + "arka1.png").toURI().toString()));
            computerCard5.setImage(new Image(new File(basePath + "arka1.png").toURI().toString()));
            computerCard6.setImage(new Image(new File(basePath + "arka1.png").toURI().toString()));

            // Oyuncu kartları
            playerCard1.setImage(new Image(new File(basePath + "card1.png").toURI().toString()));
            playerCard2.setImage(new Image(new File(basePath + "card2.png").toURI().toString()));
            playerCard3.setImage(new Image(new File(basePath + "card3.png").toURI().toString()));
            playerCard4.setImage(new Image(new File(basePath + "card4.png").toURI().toString()));
            playerCard5.setImage(new Image(new File(basePath + "card2.png").toURI().toString()));
            playerCard6.setImage(new Image(new File(basePath + "card3.png").toURI().toString()));

        } catch (Exception e) {
            System.out.println("Resim yükleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupCardClickEvents() {
        // Tüm oyuncu kartları için tek bir click handler
        playerCard1.setOnMouseClicked(event -> handleCardSelection(playerCard1, 1));
        playerCard2.setOnMouseClicked(event -> handleCardSelection(playerCard2, 2));
        playerCard3.setOnMouseClicked(event -> handleCardSelection(playerCard3, 3));
        playerCard4.setOnMouseClicked(event -> handleCardSelection(playerCard4, 4));
        playerCard5.setOnMouseClicked(event -> handleCardSelection(playerCard5, 5));
        playerCard6.setOnMouseClicked(event -> handleCardSelection(playerCard6, 6));

        // Seçili kartlar için tıklama olayları
        playerSelectedCard1.setOnMouseClicked(event -> returnCardToOriginal(playerSelectedCard1, 0));
        playerSelectedCard2.setOnMouseClicked(event -> returnCardToOriginal(playerSelectedCard2, 1));
        playerSelectedCard3.setOnMouseClicked(event -> returnCardToOriginal(playerSelectedCard3, 2));
    }

    private void handleCardSelection(ImageView sourceCard, int cardIndex) {
        if (sourceCard.getImage() != null && selectedCardCount < 3) {
            ImageView targetCard;
            switch (selectedCardCount) {
                case 0:
                    targetCard = playerSelectedCard1;
                    break;
                case 1:
                    targetCard = playerSelectedCard2;
                    break;
                case 2:
                    targetCard = playerSelectedCard3;
                    break;
                default:
                    return;
            }

            if (targetCard.getImage() == null) {
                targetCard.setImage(sourceCard.getImage());
                sourceCard.setImage(null);
                selectedToOriginalMap.put(targetCard, sourceCard);
                selectedCardCount++;
                //updateHealthAndLevel(cardIndex);
            }
        }
    }

    private void returnCardToOriginal(ImageView selectedCard, int position) {
        if (selectedCard.getImage() != null) {
            ImageView originalCard = selectedToOriginalMap.get(selectedCard);
            if (originalCard != null) {
                originalCard.setImage(selectedCard.getImage());
                selectedCard.setImage(null);
                selectedToOriginalMap.remove(selectedCard);
                selectedCardCount--;
            }
        }
    }

    // Can ve seviye göstergelerini güncelle
    /*private void updateHealthAndLevel(int cardIndex) {
        if (cardIndex == 1) {
            playerCard1Health -= 10;  // Örnek olarak canı 10 azaltıyoruz
            playerCard1Level++;
            playerCard1HealthLabel.setText(String.valueOf(playerCard1Health));
            playerCard1LevelLabel.setText(String.valueOf(playerCard1Level));
        } else if (cardIndex == 2) {
            playerCard2Health -= 10;
            playerCard2Level++;
            playerCard2HealthLabel.setText(String.valueOf(playerCard2Health));
            playerCard2LevelLabel.setText(String.valueOf(playerCard2Level));
        } else if (cardIndex == 3) {
            playerCard3Health -= 10;
            playerCard3Level++;
            playerCard3HealthLabel.setText(String.valueOf(playerCard3Health));
            playerCard3LevelLabel.setText(String.valueOf(playerCard3Level));
        }
    }*/

    // Can göstergelerini animasyonlu hale getir
    private void setupHealthIndicators() {
        setupHealthIndicatorAnimation(playerCard1HealthCircle);
        setupHealthIndicatorAnimation(playerCard2HealthCircle);
        setupHealthIndicatorAnimation(playerCard3HealthCircle);
        setupHealthIndicatorAnimation(playerCard4HealthCircle);
        setupHealthIndicatorAnimation(playerCard5HealthCircle);
        setupHealthIndicatorAnimation(playerCard6HealthCircle);

    }

    private void setupHealthIndicatorAnimation(Circle healthCircle) {
        FillTransition fillTransition = new FillTransition(Duration.seconds(1), healthCircle);
        fillTransition.setFromValue(Color.LIGHTBLUE);
        fillTransition.setToValue(Color.RED);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
    }
}
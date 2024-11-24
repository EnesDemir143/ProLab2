package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class UIController {
    @FXML private HBox playerCardsContainer; // Kartları içerecek HBox
    @FXML private HBox selectedCardsContainer; // Seçili kartları gösterecek HBox
    @FXML private Button finishButton;

    private List<Card> currentHand; // Eldeki kartlar
    private List<ImageView> selectedCards = new ArrayList<>();
    private List<StackPane> selectedCardSlots = new ArrayList<>();
    private static final int MAX_SELECTED_CARDS = 3;

    // Kart türleri için enum
    public enum CardType {
        TYPE1("type1.png"),
        TYPE2("type2.png"),
        TYPE3("type3.png"),
        TYPE4("type4.png"),
        TYPE5("type5.png"),
        TYPE6("type6.png");

        private final String imagePath;

        CardType(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getImagePath() {
            return imagePath;
        }
    }

    // Kart sınıfı
    public static class Card {
        private final CardType type;
        private final Image image;

        public Card(CardType type) {
            this.type = type;
            this.image = new Image(getClass().getResourceAsStream("/images/" + type.getImagePath()));
        }

        public CardType getType() {
            return type;
        }

        public Image getImage() {
            return image;
        }
    }

    @FXML
    public void initialize() {
        finishButton.setDisable(true);
    }

    // Yeni eli göstermek için çağrılacak metod
    public void updateHand(List<Card> newHand) {
        this.currentHand = newHand;
        playerCardsContainer.getChildren().clear();
        selectedCards.clear();
        selectedCardSlots.clear();

        for (Card card : newHand) {
            createAndAddCardToUI(card);
        }
    }

    private void createAndAddCardToUI(Card card) {
        // Kart görüntüsünü oluştur
        ImageView cardImage = new ImageView(card.getImage());
        cardImage.setFitHeight(150); // Kart yüksekliği
        cardImage.setFitWidth(100);  // Kart genişliği

        // StackPane oluştur
        StackPane cardPane = new StackPane(cardImage);
        cardPane.getStyleClass().add("player-card-unselected");

        // Kart seçim olayını ayarla
        setupCardSelection(cardPane, card);

        // Hover efektini ekle
        setupCardHoverEffect(cardPane);

        // HBox'a ekle
        playerCardsContainer.getChildren().add(cardPane);
    }

    private void setupCardSelection(StackPane cardPane, Card card) {
        cardPane.setOnMouseClicked(event -> {
            ImageView cardImage = (ImageView) cardPane.getChildren().get(0);

            if (cardPane.getStyleClass().contains("player-card-selected")) {
                returnCardToSlot(cardPane, cardImage);
            } else if (selectedCards.size() < MAX_SELECTED_CARDS) {
                selectCardFromSlot(cardPane, cardImage, card);
            } else {
                showMaxCardWarning();
            }

            finishButton.setDisable(selectedCards.size() != MAX_SELECTED_CARDS);
        });
    }

    private void setupCardHoverEffect(StackPane cardPane) {
        cardPane.setOnMouseEntered(e -> {
            if (!cardPane.getStyleClass().contains("player-card-selected")) {
                cardPane.getStyleClass().add("player-card-hover");
            }
        });

        cardPane.setOnMouseExited(e -> {
            cardPane.getStyleClass().remove("player-card-hover");
        });
    }

    private void selectCardFromSlot(StackPane cardPane, ImageView cardImage, Card card) {
        cardImage.setVisible(false);
        cardPane.getStyleClass().add("player-card-empty");

        var selectedCard = new ImageView(card.getImage());
        selectedCard.setFitHeight(cardImage.getFitHeight());
        selectedCard.setFitWidth(cardImage.getFitWidth());
        selectedCards.add(selectedCard);
        selectedCardSlots.add(cardPane);

        cardPane.getStyleClass().remove("player-card-unselected");
        cardPane.getStyleClass().add("player-card-selected");
        playSelectAnimation(cardPane);

        updateSelectedCardDisplay();
    }

    private void returnCardToSlot(StackPane cardPane, ImageView cardImage) {
        int index = selectedCardSlots.indexOf(cardPane);
        if (index != -1) {
            cardImage.setVisible(true);
            cardPane.getStyleClass().remove("player-card-empty");

            selectedCards.remove(index);
            selectedCardSlots.remove(index);

            cardPane.getStyleClass().remove("player-card-selected");
            cardPane.getStyleClass().add("player-card-unselected");

            updateSelectedCardDisplay();
        }
    }

    private void updateSelectedCardDisplay() {
        selectedCardsContainer.getChildren().clear();

        for (ImageView selectedCard : selectedCards) {
            StackPane selectedCardPane = new StackPane(selectedCard);
            selectedCardsContainer.getChildren().add(selectedCardPane);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), selectedCardPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
    }

    private void playSelectAnimation(StackPane cardPane) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), cardPane);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(2);
        scaleTransition.play();
    }

    private void showMaxCardWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Uyarı");
        alert.setHeaderText(null);
        alert.setContentText("En fazla 3 kart seçebilirsiniz!");
        alert.showAndWait();
    }

    @FXML
    private void finishGame() {
        // Oyun bitirme mantığı
    }
}
// UIController.java
package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.animation.ScaleTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

public class UIController {
    @FXML private ImageView playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6;
    @FXML private ImageView playerSelectedCard1, playerSelectedCard2, playerSelectedCard3;
    @FXML private StackPane playerCard1Indicator, playerCard2Indicator, playerCard3Indicator,
            playerCard4Indicator, playerCard5Indicator, playerCard6Indicator;
    @FXML private Button finishButton;

    private List<ImageView> selectedCards = new ArrayList<>();
    private List<StackPane> selectedCardSlots = new ArrayList<>();
    private static final int MAX_SELECTED_CARDS = 3;

    @FXML
    public void initialize() {
        setupPlayerCards();
        setupCardSelectionEffect();
    }

    private void setupPlayerCards() {
        List<StackPane> playerCards = List.of(
                playerCard1Indicator, playerCard2Indicator, playerCard3Indicator,
                playerCard4Indicator, playerCard5Indicator, playerCard6Indicator
        );

        for (StackPane cardPane : playerCards) {
            cardPane.getStyleClass().add("player-card-unselected");
            setupCardSelection(cardPane);
        }

        finishButton.setDisable(true);
    }

    private void setupCardSelection(StackPane cardPane) {
        cardPane.setOnMouseClicked(event -> {
            ImageView cardImage = (ImageView) cardPane.getChildren().get(0);

            if (cardPane.getStyleClass().contains("player-card-selected")) {
                returnCardToSlot(cardPane, cardImage);
            } else if (selectedCards.size() < MAX_SELECTED_CARDS) {
                selectCardFromSlot(cardPane, cardImage);
            } else {
                showMaxCardWarning();
            }

            finishButton.setDisable(selectedCards.size() != MAX_SELECTED_CARDS);
        });
    }

    private void selectCardFromSlot(StackPane cardPane, ImageView cardImage) {
        // Orijinal kartın görüntüsünü kaydet
        var originalImage = cardImage.getImage();

        // Kartı görünmez yap ve slot stilini değiştir
        cardImage.setVisible(false);
        cardPane.getStyleClass().add("player-card-empty");

        // Seçili kartlar listesine ekle
        var selectedCard = new ImageView(originalImage);
        selectedCard.setFitHeight(cardImage.getFitHeight());
        selectedCard.setFitWidth(cardImage.getFitWidth());
        selectedCards.add(selectedCard);
        selectedCardSlots.add(cardPane);

        // Görsel efektler
        cardPane.getStyleClass().remove("player-card-unselected");
        cardPane.getStyleClass().add("player-card-selected");
        playSelectAnimation(cardPane);

        updateSelectedCardDisplay();
    }

    private void returnCardToSlot(StackPane cardPane, ImageView cardImage) {
        int index = selectedCardSlots.indexOf(cardPane);
        if (index != -1) {
            // Kartı görünür yap ve boş slot stilini kaldır
            cardImage.setVisible(true);
            cardPane.getStyleClass().remove("player-card-empty");

            // Listeleri güncelle
            selectedCards.remove(index);
            selectedCardSlots.remove(index);

            // Görsel efektleri kaldır
            cardPane.getStyleClass().remove("player-card-selected");
            cardPane.getStyleClass().add("player-card-unselected");

            updateSelectedCardDisplay();
        }
    }

    private void setupCardSelectionEffect() {
        List<StackPane> allCards = List.of(
                playerCard1Indicator, playerCard2Indicator, playerCard3Indicator,
                playerCard4Indicator, playerCard5Indicator, playerCard6Indicator
        );

        for (StackPane card : allCards) {
            card.setOnMouseEntered(e -> {
                if (!card.getStyleClass().contains("player-card-selected")) {
                    card.getStyleClass().add("player-card-hover");
                }
            });

            card.setOnMouseExited(e -> {
                card.getStyleClass().remove("player-card-hover");
            });
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

    private void updateSelectedCardDisplay() {
        List<ImageView> selectedSlots = List.of(playerSelectedCard1, playerSelectedCard2, playerSelectedCard3);

        // Tüm slotları temizle
        for (ImageView slot : selectedSlots) {
            slot.setImage(null);
        }

        // Seçili kartları göster
        for (int i = 0; i < selectedCards.size(); i++) {
            selectedSlots.get(i).setImage(selectedCards.get(i).getImage());

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), selectedSlots.get(i));
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        }
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
        // Oyun bitirme mantığı buraya gelecek
    }
}

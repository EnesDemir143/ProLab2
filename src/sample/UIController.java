package sample;

import Game.Oyuncu;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;

public class UIController implements Initializable {
    @FXML private HBox computerCardsContainer;
    @FXML private HBox computerSelectedCardsContainer;
    @FXML private HBox playerCardsContainer;
    @FXML private HBox playerSelectedCardsContainer;
    @FXML private Button finishButton;

    private Oyuncu playerOyuncu;
    private Oyuncu pcOyuncu;
    private Map<ImageView, ImageView> selectedToOriginalMap = new HashMap<>();
    private int selectedCardCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("UIController initialized");
        assert computerCardsContainer != null : "computerCardsContainer FXML yüklenemedi";
        assert playerCardsContainer != null : "playerCardsContainer FXML yüklenemedi";
        assert computerSelectedCardsContainer != null : "computerSelectedCardsContainer FXML yüklenemedi";
        assert playerSelectedCardsContainer != null : "playerSelectedCardsContainer FXML yüklenemedi";

        if (finishButton != null) {
            finishButton.setOnAction(e -> handleFinishTurn());
        }
    }

    private void handleFinishTurn() {
        if (selectedCardCount == 3) {
            // İşlem tamamlandı, seçili kartları resetle
            selectedCardCount = 0;
            selectedToOriginalMap.clear();
            playerSelectedCardsContainer.getChildren().clear();

            // Tüm kartların opaklığını normale döndür
            playerCardsContainer.getChildren().forEach(node -> node.setOpacity(1.0));

            // Bilgisayarın kart seçimini simüle et
            simulateComputerCardSelection();
        }
    }

    private void simulateComputerCardSelection() {
        computerSelectedCardsContainer.getChildren().clear();
        Random random = new Random();

        // Bilgisayarın 3 kart seçmesi
        for (int i = 0; i < 3; i++) {
            if (computerCardsContainer.getChildren().isEmpty()) break;

            int randomIndex = random.nextInt(computerCardsContainer.getChildren().size());
            StackPane selectedCard = (StackPane) computerCardsContainer.getChildren().get(randomIndex);

            // Seçilen kartı kopyala ve seçili kartlar bölümüne ekle
            StackPane cardCopy = new StackPane(new ImageView(((ImageView)selectedCard.getChildren().get(0)).getImage()));
            computerSelectedCardsContainer.getChildren().add(cardCopy);
        }
    }

    public void setGameLists(Oyuncu playerOyuncu, Oyuncu pcOyuncu) {
        if (playerOyuncu == null || pcOyuncu == null) {
            throw new IllegalArgumentException("Oyuncu nesneleri null olamaz");
        }

        this.playerOyuncu = playerOyuncu;
        this.pcOyuncu = pcOyuncu;

        Platform.runLater(() -> {
            createPlayerCards();
            createComputerCards();
        });
    }

    private void createPlayerCards() {
        playerCardsContainer.getChildren().clear();
        ArrayList<Savas_Araclari> playerCards = playerOyuncu.getInsanKart();

        for (Savas_Araclari card : playerCards) {
            StackPane cardPane = createCardPane(card, false);
            playerCardsContainer.getChildren().add(cardPane);
        }
    }

    private void createComputerCards() {
        computerCardsContainer.getChildren().clear();
        ArrayList<Savas_Araclari> computerCards = pcOyuncu.getBilgisayarKart();

        for (int i = 0; i < computerCards.size(); i++) {
            StackPane cardPane = createCardPane(null, true);
            computerCardsContainer.getChildren().add(cardPane);
        }
    }

    private StackPane createCardPane(Savas_Araclari card, boolean isComputer) {
        StackPane cardPane = new StackPane();
        cardPane.setMinSize(100, 150);

        ImageView cardView = new ImageView();
        cardView.setFitHeight(130);
        cardView.setFitWidth(90);
        cardView.setPreserveRatio(true);

        try {
            String imagePath = isComputer ? "/sample/arka1.png" : "/sample/card3.png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            cardView.setImage(image);
        } catch (Exception e) {
            System.err.println("Kart resmi yüklenemedi: " + e.getMessage());
        }

        if (!isComputer && card != null) {
            VBox details = new VBox(5);
            details.setAlignment(Pos.BOTTOM_CENTER);

            Label typeLabel = new Label(card.getSinif());
            Label strengthLabel = new Label("Güç: " + card.getVurus());
            Label healthLabel = new Label("Can: " + card.getDayaniklilik());

            typeLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
            strengthLabel.setStyle("-fx-text-fill: white;");
            healthLabel.setStyle("-fx-text-fill: white;");

            details.getChildren().addAll(typeLabel, strengthLabel, healthLabel);
            cardPane.getChildren().addAll(cardView, details);

            // Kart seçim efektleri
            cardView.setOnMouseEntered(e -> cardPane.setEffect(new DropShadow(20, Color.YELLOW)));
            cardView.setOnMouseExited(e -> cardPane.setEffect(null));
            cardView.setOnMouseClicked(e -> handleCardSelection(cardView, cardPane));
        } else {
            cardPane.getChildren().add(cardView);
        }

        return cardPane;
    }

    private void handleCardSelection(ImageView cardView, StackPane cardPane) {
        if (selectedCardCount < 3 && !selectedToOriginalMap.containsKey(cardView)) {
            // Yeni bir kart kopyası oluştur
            StackPane selectedPane = new StackPane(new ImageView(cardView.getImage()));
            selectedPane.setMinSize(100, 150);

            // Seçili kartlar bölümüne ekle
            playerSelectedCardsContainer.getChildren().add(selectedPane);

            // Seçili kartı işaretle
            selectedToOriginalMap.put(cardView, cardView);
            cardPane.setOpacity(0.5);
            selectedCardCount++;

            // Maksimum kart seçildiğinde kullanıcıyı bilgilendir
            if (selectedCardCount == 3) {
                System.out.println("Maksimum kart seçildi!");
            }
        }
    }

    public void updateGameState() {
        Platform.runLater(() -> {
            createPlayerCards();
            createComputerCards();
            selectedCardCount = 0;
            selectedToOriginalMap.clear();
            playerSelectedCardsContainer.getChildren().clear();
            computerSelectedCardsContainer.getChildren().clear();
        });
    }
}
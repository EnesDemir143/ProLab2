package sample;

import Game.Oyuncu;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.InputStream;
import java.net.URL;
import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

enum CardImage {
    CARD_BACK("/sample/arka1.png"),
    PLAYER_CARD("/sample/card3.png");

    private final String path;

    CardImage(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}

public class UIController implements Initializable {
    @FXML private HBox computerCardsContainer;
    @FXML private HBox computerSelectedCardsContainer;
    @FXML private HBox playerCardsContainer;
    @FXML private HBox playerSelectedCardsContainer;
    @FXML private Button finishButton;

    private Oyuncu playerOyuncu;
    private Oyuncu pcOyuncu;

    private List<ImageView> computerCards = new ArrayList<>();
    private List<ImageView> playerCards = new ArrayList<>();
    private List<ImageView> computerSelectedCards = new ArrayList<>();
    private List<ImageView> playerSelectedCards = new ArrayList<>();

    private List<Integer> playerCardHealths = new ArrayList<>();
    private List<Integer> playerCardLevels = new ArrayList<>();

    private List<Label> playerCardHealthLabels = new ArrayList<>();
    private List<Circle> playerCardHealthCircles = new ArrayList<>();

    private Map<ImageView, ImageView> selectedToOriginalMap = new HashMap<>();
    private int selectedCardCount = 0;

    private List<String> playerCardTypes = new ArrayList<>();
    private List<Integer> playerCardStrengths = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initialize called");
        System.out.println("playerCardsContainer: " + (playerCardsContainer != null));
        System.out.println("computerCardsContainer: " + (computerCardsContainer != null));
        setupFinishButton();

        playerCardTypes = new ArrayList<>();
        playerCardStrengths = new ArrayList<>();
        playerCardHealths = new ArrayList<>();
        playerCardLevels = new ArrayList<>();
    }

    private void setupFinishButton() {
        finishButton.setOnAction(event -> handleFinishButton());
    }
    private void createCardViews(int cardCount, HBox containerBox, List<ImageView> cardList,
                                 List<Label> healthLabels, List<Circle> healthCircles,
                                 List<Integer> healthValues, List<Integer> cardLevels,
                                 boolean isComputerCards) {

        Platform.runLater(() -> {
            if (containerBox == null) {
                System.err.println("Container box is null");
                return;
            }

            containerBox.getChildren().clear();
            cardList.clear();

            for (int i = 0; i < cardCount; i++) {
                StackPane cardStackPane = new StackPane();
                cardStackPane.setMinSize(70, 110);
                cardStackPane.setPrefSize(70, 110);

                ImageView cardImageView = new ImageView();
                cardImageView.setFitHeight(100.0);
                cardImageView.setFitWidth(60.0);
                cardImageView.setPreserveRatio(true);

                try {
                    String imagePath = isComputerCards ? "/sample/arka1.png" : "/sample/card3.png";
                    InputStream imageStream = getClass().getResourceAsStream(imagePath);

                    if (imageStream != null) {
                        Image image = new Image(imageStream);
                        cardImageView.setImage(image);
                        System.out.println("Card image loaded successfully: " + imagePath);
                    } else {
                        System.err.println("Failed to load image: " + imagePath);
                        // Yedek çözüm: Basit bir dikdörtgen ile kartı temsil et
                        Rectangle placeholder = new Rectangle(60, 100);
                        placeholder.setFill(isComputerCards ? Color.RED : Color.BLUE);
                        placeholder.setStroke(Color.BLACK);
                        cardStackPane.getChildren().add(placeholder);
                    }
                } catch (Exception e) {
                    System.err.println("Error loading card image: " + e.getMessage());
                    e.printStackTrace();
                }

                // Kart tıklama işlemleri için event handler ekle
                final int index = i;
                cardImageView.setOnMouseClicked(event -> {
                    if (!isComputerCards) {
                        handleCardSelection(cardImageView, index);
                    }
                });

                cardStackPane.getChildren().add(cardImageView);
                containerBox.getChildren().add(cardStackPane);
                cardList.add(cardImageView);
            }
        });
    }

    private void setupHealthIndicatorAnimation(Circle healthCircle) {
        FillTransition fillTransition = new FillTransition(Duration.seconds(1), healthCircle);
        fillTransition.setFromValue(Color.LIGHTBLUE);
        fillTransition.setToValue(Color.RED);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
    }

    private void handleCardSelection(ImageView sourceCard, int cardIndex) {
        if (sourceCard.getImage() != null && selectedCardCount < 3) {
            ImageView targetCard = null;
            switch (selectedCardCount) {
                case 0:
                    targetCard = playerSelectedCards.get(0);
                    break;
                case 1:
                    targetCard = playerSelectedCards.get(1);
                    break;
                case 2:
                    targetCard = playerSelectedCards.get(2);
                    break;
            }

            if (targetCard != null && targetCard.getImage() == null) {
                targetCard.setImage(sourceCard.getImage());
                sourceCard.setImage(null);
                selectedToOriginalMap.put(targetCard, sourceCard);
                selectedCardCount++;
                updateCardDetails(cardIndex - 1);
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
                resetCardDetails(position);
            }
        }
    }

    private void updateCardDetails(int cardIndex) {
        if (cardIndex >= 0 && cardIndex < playerCardHealthLabels.size()) {
            Savas_Araclari kart = playerOyuncu.getInsanKart().get(cardIndex);

            int newHealth = kart.getDayaniklilik() - 10;
            kart.setDayaniklilik(Math.max(newHealth, 0));
            playerCardHealths.set(cardIndex, kart.getDayaniklilik());
            playerCardHealthLabels.get(cardIndex).setText(String.valueOf(kart.getDayaniklilik()));

            int newLevel = playerCardLevels.get(cardIndex) + 1;
            playerCardLevels.set(cardIndex, newLevel);
        }
    }

    private void resetCardDetails(int cardIndex) {
        if (cardIndex >= 0 && cardIndex < playerCardHealthLabels.size()) {
            Savas_Araclari kart = playerOyuncu.getInsanKart().get(cardIndex);

            kart.setDayaniklilik(100);
            playerCardHealthLabels.get(cardIndex).setText("100");
            playerCardHealths.set(cardIndex, 100);
            playerCardLevels.set(cardIndex, 1);
        }
    }

    private void handleFinishButton() {
        System.out.println("Oyun bitirildi!");

        for (int i = 0; i < playerSelectedCards.size(); i++) {
            if (playerSelectedCards.get(i).getImage() != null) {
                Savas_Araclari kart = playerOyuncu.getInsanKart().get(i);

                System.out.println("Seçilen Kart " + (i+1) + ":");
                System.out.println("Tip: " + kart.getSinif());
                System.out.println("Güç: " + kart.getVurus());
                System.out.println("Can: " + kart.getDayaniklilik());
                System.out.println("---");
            }
        }
    }

    public void setGameLists(Oyuncu playerOyuncu, Oyuncu pcOyuncu) {
        System.out.println("setGameLists called");
        System.out.println("Player cards: " + playerOyuncu.getInsanKart().size());
        System.out.println("Computer cards: " + pcOyuncu.getBilgisayarKart().size());

        if (playerOyuncu == null || pcOyuncu == null) {
            System.out.println("b");
            System.err.println("Player or computer object is null");
            return;
        }

        if (playerCardsContainer == null || computerCardsContainer == null ) {
            System.out.println("c");
            System.err.println("One or more FXML containers are not initialized");
            return;
        }

        this.playerOyuncu = playerOyuncu;
        this.pcOyuncu = pcOyuncu;

        // Dynamically create card views based on the number of cards
        int playerCardCount = Math.min(playerOyuncu.getInsanKart().size(), 10);
        int computerCardCount = Math.min(pcOyuncu.getBilgisayarKart().size(), 10);

        // Clear and reset lists before populating
        playerCardTypes.clear();
        playerCardStrengths.clear();
        playerCardHealths.clear();
        playerCardLevels.clear();

        // Populate card details lists
        for (Savas_Araclari kart : playerOyuncu.getInsanKart()) {
            playerCardTypes.add(kart.getSinif());
            playerCardStrengths.add(kart.getVurus());
            playerCardHealths.add(kart.getDayaniklilik());
            playerCardLevels.add(1); // Default starting level
        }

        // Create player cards
        createCardViews(playerCardCount, playerCardsContainer, playerCards,
                playerCardHealthLabels, playerCardHealthCircles,
                playerCardHealths, playerCardLevels, false);

        // Create computer cards
        createCardViews(computerCardCount, computerCardsContainer, computerCards,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), true);

        // Create selected card containers
        createCardViews(3, playerSelectedCardsContainer, playerSelectedCards,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), false);
        createCardViews(3, computerSelectedCardsContainer, computerSelectedCards,
                new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), true);

        updateUIFromLists();
    }

    private void updateUIFromLists() {
        if (playerOyuncu != null) {
            ArrayList<Savas_Araclari> insanKartlari = playerOyuncu.getInsanKart();

            for (int i = 0; i < Math.min(insanKartlari.size(), playerCards.size()); i++) {
                Savas_Araclari kart = insanKartlari.get(i);

                playerCardTypes.set(i, kart.getSinif());
                playerCardStrengths.set(i, kart.getVurus());
                playerCardHealths.set(i, kart.getDayaniklilik());
                playerCardHealthLabels.get(i).setText(String.valueOf(kart.getDayaniklilik()));

                playerCards.get(i).setImage(new Image(new File(CardImage.PLAYER_CARD.getPath()).toURI().toString()));
            }
        }

        if (pcOyuncu != null) {
            ArrayList<Savas_Araclari> bilgisayarKartlari = pcOyuncu.getBilgisayarKart();

            for (int i = 0; i < Math.min(bilgisayarKartlari.size(), computerCards.size()); i++) {
                computerCards.get(i).setImage(new Image(new File(CardImage.CARD_BACK.getPath()).toURI().toString()));
            }
        }
    }

    public void updateSpecificPlayer(int index, Savas_Araclari kart) {
        if (playerOyuncu != null && index >= 0 && index < playerOyuncu.getInsanKart().size()) {
            playerOyuncu.getInsanKart().set(index, kart);

            if (index < playerCardTypes.size()) {
                playerCardTypes.set(index, kart.getSinif());
                playerCardStrengths.set(index, kart.getVurus());
                playerCardHealths.set(index, kart.getDayaniklilik());
                playerCardHealthLabels.get(index).setText(String.valueOf(kart.getDayaniklilik()));
            }
        }
    }

    // Getter metodları
    public List<String> getPlayerCardTypes() {
        return playerCardTypes;
    }

    public List<Integer> getPlayerCardStrengths() {
        return playerCardStrengths;
    }

    public List<Integer> getPlayerCardHealths() {
        return playerCardHealths;
    }

    public List<Integer> getPlayerCardLevels() {
        return playerCardLevels;
    }
}
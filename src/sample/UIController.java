package sample;

import Game.Dosya_Islemleri;
import Game.Oyuncu;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
import javafx.scene.shape.Circle;
import javafx.animation.*;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.util.Duration;
import javafx.scene.effect.Glow;

import java.net.URL;
import java.util.*;

public class UIController implements Initializable, Dosya_Islemleri {
    @FXML private HBox computerCardsContainer;
    @FXML private HBox computerSelectedCardsContainer;
    @FXML private HBox playerCardsContainer;
    @FXML private HBox playerSelectedCardsContainer;
    @FXML private Button finishButton;
    @FXML private Label playerScoreLabel;
    @FXML private Label computerScoreLabel;

    private Controller instance;
    private int countRound = 1;
    private int kontrol = 0;
    private int selectedCardIndex = 0;
    private Oyuncu playerOyuncu;
    private Oyuncu pcOyuncu;
    private Map<ImageView, ImageView> selectedToOriginalMap = new HashMap<>();
    private int selectedCardCount = 0;
    private boolean cardsConfirmed = false;
    private DropShadow glowEffectHover  ;


    public void setInstance(Controller instance) {
        this.instance = instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setupInitialState();
        setupFinishButton();
    }

    private void setupInitialState() {
        if (finishButton != null) {
            finishButton.setDisable(true);
        }
    }

    private void setupFinishButton() {
        if (finishButton != null) {
            finishButton.setOnAction(e -> handleFinishButtonClick());
        }
    }

    private void handleFinishButtonClick() {
        if (!cardsConfirmed) {
            cardsConfirmed = true;
            showComputerSelectedCards();
            Platform.runLater(this::performBattle);
        } else {
            animateCardBattle(() -> proceedToNextRound());
        }
    }

    private void showComputerSelectedCards() {
        computerSelectedCardsContainer.getChildren().clear();
        for (Savas_Araclari card : instance.getPcSeckart()) {
            StackPane cardPane = createCardPane(card, false);
            computerSelectedCardsContainer.getChildren().add(cardPane);
        }
    }

    private void performBattle() {
        Oyuncu.kartSavaslari(instance.getController().getOyuncu(),
                instance.getController().getPc(),
                instance.getInsanSeckart(),
                instance.getPcSeckart());

        if (kontrol != 7 && kontrol != 8) {
            kontrol = Game.Oyuncu.savasSonuclari(instance.getController().getOyuncu(),
                    instance.getController().getPc(), countRound, 0);
        } else {
            kontrol = Game.Oyuncu.savasSonuclari(instance.getController().getOyuncu(),
                    instance.getController().getPc(), countRound, 1);
        }

        instance.getController().getOyuncu().destendekiKartlar(
                instance.getController().getOyuncu(),
                instance.getController().getPc(),
                instance.getController().getOyuncu().getInsanKart(),
                instance.getController().getPc().getBilgisayarKart());

        if (isGameOver()) {
            handleGameOver();
        }

        finishButton.setText("Savaş");
        updateScores();
    }
    private void animateCardBattle(Runnable onComplete) {
        Timeline timeline = new Timeline();

        List<Node> playerCards = new ArrayList<>(playerSelectedCardsContainer.getChildren());
        List<Node> computerCards = new ArrayList<>(computerSelectedCardsContainer.getChildren());

        Map<Node, Point2D> originalPositions = new HashMap<>();
        playerCards.forEach(card -> originalPositions.put(card, new Point2D(card.getTranslateX(), card.getTranslateY())));
        computerCards.forEach(card -> originalPositions.put(card, new Point2D(card.getTranslateX(), card.getTranslateY())));

        for (int i = 0; i < Math.min(playerCards.size(), computerCards.size()); i++) {
            Node playerCard = playerCards.get(i);
            Node computerCard = computerCards.get(i);

            // Add dramatic battle animations
            KeyFrame playerAttack = new KeyFrame(Duration.millis(400),
                    new KeyValue(playerCard.translateYProperty(), -80),
                    new KeyValue(playerCard.rotateProperty(), -25),
                    new KeyValue(playerCard.scaleXProperty(), 1.2),
                    new KeyValue(playerCard.scaleYProperty(), 1.2),
                    new KeyValue(playerCard.opacityProperty(), 0.7)
            );

            KeyFrame computerAttack = new KeyFrame(Duration.millis(400),
                    new KeyValue(computerCard.translateYProperty(), 80),
                    new KeyValue(computerCard.rotateProperty(), 25),
                    new KeyValue(computerCard.scaleXProperty(), 1.2),
                    new KeyValue(computerCard.scaleYProperty(), 1.2),
                    new KeyValue(computerCard.opacityProperty(), 0.7)
            );

            KeyFrame playerReturn = new KeyFrame(Duration.millis(600),
                    new KeyValue(playerCard.translateYProperty(), originalPositions.get(playerCard).getY()),
                    new KeyValue(playerCard.rotateProperty(), 0),
                    new KeyValue(playerCard.scaleXProperty(), 1),
                    new KeyValue(playerCard.scaleYProperty(), 1),
                    new KeyValue(playerCard.opacityProperty(), 1)
            );

            KeyFrame computerReturn = new KeyFrame(Duration.millis(600),
                    new KeyValue(computerCard.translateYProperty(), originalPositions.get(computerCard).getY()),
                    new KeyValue(computerCard.rotateProperty(), 0),
                    new KeyValue(computerCard.scaleXProperty(), 1),
                    new KeyValue(computerCard.scaleYProperty(), 1),
                    new KeyValue(computerCard.opacityProperty(), 1)
            );

            // Clash effect with vibration
            KeyFrame clashEffect = new KeyFrame(Duration.millis(450), event -> {
                Timeline vibeTimeline = new Timeline(
                        new KeyFrame(Duration.millis(50),
                                new KeyValue(playerCard.translateXProperty(), -5),
                                new KeyValue(computerCard.translateXProperty(), 5)
                        ),
                        new KeyFrame(Duration.millis(100),
                                new KeyValue(playerCard.translateXProperty(), 5),
                                new KeyValue(computerCard.translateXProperty(), -5)
                        ),
                        new KeyFrame(Duration.millis(150),
                                new KeyValue(playerCard.translateXProperty(), 0),
                                new KeyValue(computerCard.translateXProperty(), 0)
                        )
                );
                vibeTimeline.play();
            });

            timeline.getKeyFrames().addAll(playerAttack, computerAttack, clashEffect, playerReturn, computerReturn);
        }

        timeline.setOnFinished(event -> {
            playerSelectedCardsContainer.setEffect(new Glow(0.8));
            computerSelectedCardsContainer.setEffect(new Glow(0.8));

            PauseTransition pause = new PauseTransition(Duration.millis(200));
            pause.setOnFinished(e -> {
                playerSelectedCardsContainer.setEffect(null);
                computerSelectedCardsContainer.setEffect(null);
                onComplete.run();
            });
            pause.play();
        });

        timeline.play();
    }

    private void proceedToNextRound() {
        cardsConfirmed = false;
        instance.getController().getPcSeckart().clear();
        instance.getController().getInsanSeckart().clear();
        selectedCardCount = 0;
        updateGameState();
        setGameLists(playerOyuncu, pcOyuncu);
        countRound++;
        finishButton.setText("Seçimleri Onayla");
        finishButton.setDisable(true);
    }

    private void handleCardClick(StackPane cardPane, Savas_Araclari card) {
        if (cardsConfirmed) return;
        if(selectedCardCount>3){
            showWarningAlert("Hata", "Maksimum 3 kart seçebilirsiniz!");
        }
        if (isCardPreviouslyUsed(card, cardPane)) {
            showWarningAlert("Hata", "Bu kart daha önce kullanılmış. Önce hiç kullanılmamış olan kartlardan seçin!");
            return;
        }

        if (playerSelectedCardsContainer.getChildren().contains(cardPane)) {
            deselectCard(cardPane, card);
        } else if (selectedCardCount < 3) {
            selectCard(cardPane, card);
        } else {
            showWarningAlert("Hata", "Maksimum 3 kart seçebilirsiniz!");        }
    }

    private boolean isCardPreviouslyUsed(Savas_Araclari card, StackPane cardPane) {
        return playerOyuncu.getKullanilmisKartlarInsan().contains(card) &&
                playerOyuncu.getKullanilmisKartlarInsan().size() != instance.getOyuncu().getInsanKart().size() &&
                !playerSelectedCardsContainer.getChildren().contains(cardPane);
    }

    private void selectCard(StackPane cardPane, Savas_Araclari card) {
        playerCardsContainer.getChildren().remove(cardPane);
        playerSelectedCardsContainer.getChildren().add(cardPane);
        instance.getInsanSeckart().add(card);

        Oyuncu.secilenKartlar(
                instance.getController().getOyuncu(),
                instance.getController().getPc(),
                instance.getInsanSeckart(),
                instance.getPcSeckart(),
                selectedCardIndex
        );

        cardPane.setOpacity(1);
        selectedCardIndex++;
        selectedCardCount++;

        if (selectedCardCount == 3) {
            finishButton.setDisable(false);
            prepareBattle();
        }
    }

    private void deselectCard(StackPane cardPane, Savas_Araclari card) {
        playerSelectedCardsContainer.getChildren().remove(cardPane);
        playerCardsContainer.getChildren().add(cardPane);
        cardPane.setOpacity(1.0);
        selectedCardCount--;

        playerOyuncu.getKullanilmisKartlarInsan().remove(card);
        instance.getController().getInsanSeckart().remove(card);
        selectedCardIndex--;
        finishButton.setDisable(true);
    }

    private void prepareBattle() {
        Platform.runLater(() -> {
            instance.getController().getOyuncu().savas(
                    instance.getController().getOyuncu(),
                    instance.getController().getPc(),
                    instance.getController().getInsanSeckart(),
                    instance.getPcSeckart(),
                    countRound
            );
        });
    }

    private boolean isGameOver() {
        return kontrol == 3 || kontrol == 4 || kontrol == 5 || kontrol == 6 || kontrol == 2;
    }

    private void handleGameOver() {
        instance.getController().getOyuncu().savasSonucu(
                instance.getController().getOyuncu(),
                instance.getController().getPc());
        finishButton.setDisable(true);
        showGameOverDialog();
    }

    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oyun Bitti");
        alert.setHeaderText("Tüm turlar tamamlandı!");
        alert.setContentText(String.format(
                "Oyun bitti!\nOyuncu Skoru: %d\nBilgisayar Skoru: %d",
                instance.getController().getOyuncu().getInsanSkor(),
                instance.getController().getPc().getPcSkor()
        ));
        alert.setOnHidden(e -> Platform.runLater(() ->
                finishButton.getScene().getWindow().hide()));
        alert.showAndWait();
    }

    public void setGameLists(Oyuncu playerOyuncu, Oyuncu pcOyuncu) {
        if (playerOyuncu == null || pcOyuncu == null) {
            throw new IllegalArgumentException("Oyuncu nesneleri null olamaz");
        }

        this.playerOyuncu = playerOyuncu;
        this.pcOyuncu = pcOyuncu;

        Platform.runLater(() -> {
            updateScores();
            createPlayerCards();
            createComputerCards();
        });
    }

    private void updateScores() {
        playerScoreLabel.setText("Oyuncu Skoru: " + playerOyuncu.getInsanSkor());
        computerScoreLabel.setText("Bilgisayar Skoru: " + pcOyuncu.getPcSkor());
    }

    private void createPlayerCards() {
        playerCardsContainer.getChildren().clear();
        for (Savas_Araclari card : playerOyuncu.getInsanKart()) {
            StackPane cardPane = createCardPane(card, false);
            playerCardsContainer.getChildren().add(cardPane);
            cardPane.setOnMouseClicked(event -> handleCardClick(cardPane, card));
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
        cardPane.setMinSize(120, 180);
        cardPane.setMaxSize(120, 180);

        ImageView cardView = new ImageView();
        cardView.setFitHeight(170);
        cardView.setFitWidth(110);
        cardView.setPreserveRatio(true);

        try {
            Image image = CardImageHelper.getCardImage(
                    card != null ? card.getClass().getSimpleName() : null,
                    isComputer
            );
            cardView.setImage(image);
        } catch (Exception e) {
            System.err.println("Kart resmi yüklenemedi: " + e.getMessage());
        }

        if (!isComputer && card != null) {
            setupPlayerCardDetails(cardPane, cardView, card);
        } else {
            cardPane.getChildren().add(cardView);
        }

        return cardPane;
    }

    private void setupPlayerCardDetails(StackPane cardPane, ImageView cardView, Savas_Araclari card) {
        Circle healthCircle = new Circle(15);
        healthCircle.setFill(Color.RED);

        Label healthLabel = new Label(String.valueOf(card.getDayaniklilik()));
        healthLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        StackPane healthIndicator = new StackPane(healthCircle, healthLabel);
        glowEffectHover = new DropShadow(20, Color.YELLOWGREEN);
        glowEffectHover.setSpread(0.3);
        cardView.setOnMouseEntered(e -> cardPane.setEffect(glowEffectHover));
        cardView.setOnMouseExited(e -> cardPane.setEffect(null));

        VBox cardLayout = new VBox(5);
        cardLayout.setAlignment(Pos.CENTER);
        cardLayout.getChildren().addAll(healthIndicator, cardView);

        cardPane.getChildren().add(cardLayout);
    }

    public void updateGameState() {
        Platform.runLater(() -> {
            createPlayerCards();
            createComputerCards();
            selectedCardCount = 0;
            selectedCardIndex = 0;
            selectedToOriginalMap.clear();
            playerSelectedCardsContainer.getChildren().clear();
            computerSelectedCardsContainer.getChildren().clear();
            finishButton.setDisable(true);
            updateScores();
        });
    }
    private void showWarningAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

}
class CardImageHelper {
    public static Image getCardImage(String sinif, boolean isBackface) {
        if (isBackface) {
            return new Image(Objects.requireNonNull(CardImageHelper.class.getResourceAsStream("/sample/arka1.png")));
        }

        try {
            CardTypes cardType = CardTypes.fromString(sinif);
            return new Image(Objects.requireNonNull(CardImageHelper.class.getResourceAsStream(cardType.getImagePath())));
        } catch (Exception e) {
            System.err.println("Kart resmi yüklenemedi: " + e.getMessage());
            // Yedek olarak varsayılan kart görselini döndür
            return new Image(Objects.requireNonNull(CardImageHelper.class.getResourceAsStream("/sample/card3.png")));
        }
    }
}
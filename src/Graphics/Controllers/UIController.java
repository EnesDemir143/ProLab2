package Graphics.Controllers;

import Game.Dosya_Islemleri;
import Game.Oyuncu;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.fxml.Initializable;
import javafx.scene.shape.Circle;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;
import Graphics.CardTypes;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

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
            // Animasyonları başlat ve callback ile oyun bitişini kontrol et
            animateCardBattle(() -> {
                if (isGameOver()) {
                    // Animasyonlar bittikten 1 saniye sonra oyun bitişi işle
                    PauseTransition pause = new PauseTransition(Duration.seconds(0));
                    pause.setOnFinished(event -> handleGameOver());
                    pause.play();
                } else {
                    proceedToNextRound(); // Yeni tur başlat
                }
            });
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


        finishButton.setText("Savaş");
        updateScores();
    }
    private void animateCardBattle(Runnable onComplete) {
        List<Node> playerCards = new ArrayList<>(playerSelectedCardsContainer.getChildren());
        List<Node> computerCards = new ArrayList<>(computerSelectedCardsContainer.getChildren());

        if (playerCards.isEmpty() || computerCards.isEmpty()) {
            Platform.runLater(() -> {
                PauseTransition finalPause = new PauseTransition(Duration.seconds(1));
                finalPause.setOnFinished(event -> onComplete.run());
                finalPause.play();
            });
            return;
        }

        int cardCount = Math.min(playerCards.size(), computerCards.size());
        final AtomicInteger battleIndex = new AtomicInteger(0);

        Runnable performCardBattle = new Runnable() {
            @Override
            public void run() {
                if (battleIndex.get() < cardCount) {
                    int currentIndex = battleIndex.getAndIncrement();

                    if (currentIndex >= playerCards.size() || currentIndex >= computerCards.size()) {
                        finishBattle();
                        return;
                    }

                    Node playerCard = playerCards.get(currentIndex);
                    Node computerCard = computerCards.get(currentIndex);

                    Timeline cardBattleTimeline = new Timeline();

                    // Attack animation
                    KeyFrame playerAttack = new KeyFrame(Duration.millis(400),
                            new KeyValue(playerCard.translateYProperty(), -80),
                            new KeyValue(playerCard.rotateProperty(), -25),
                            new KeyValue(playerCard.scaleXProperty(), 1.2),
                            new KeyValue(playerCard.scaleYProperty(), 1.2)
                    );

                    KeyFrame computerAttack = new KeyFrame(Duration.millis(400),
                            new KeyValue(computerCard.translateYProperty(), 80),
                            new KeyValue(computerCard.rotateProperty(), 25),
                            new KeyValue(computerCard.scaleXProperty(), 1.2),
                            new KeyValue(computerCard.scaleYProperty(), 1.2)
                    );

                    KeyFrame resetPlayerCard = new KeyFrame(Duration.millis(800),
                            new KeyValue(playerCard.translateYProperty(), 0, Interpolator.EASE_BOTH),
                            new KeyValue(playerCard.rotateProperty(), 0, Interpolator.EASE_BOTH),
                            new KeyValue(playerCard.scaleXProperty(), 1, Interpolator.EASE_BOTH),
                            new KeyValue(playerCard.scaleYProperty(), 1, Interpolator.EASE_BOTH)
                    );

                    KeyFrame resetComputerCard = new KeyFrame(Duration.millis(800),
                            new KeyValue(computerCard.translateYProperty(), 0, Interpolator.EASE_BOTH),
                            new KeyValue(computerCard.rotateProperty(), 0, Interpolator.EASE_BOTH),
                            new KeyValue(computerCard.scaleXProperty(), 1, Interpolator.EASE_BOTH),
                            new KeyValue(computerCard.scaleYProperty(), 1, Interpolator.EASE_BOTH)
                    );

                    // Impact effect
                    KeyFrame clashEffect = new KeyFrame(Duration.millis(450), event -> {
                        boolean playerCardEliminated = safelyCheckCardElimination(currentIndex, true);
                        boolean computerCardEliminated = safelyCheckCardElimination(currentIndex, false);

                        if (playerCardEliminated) {
                            createCardEliminationAnimation(playerCard, playerSelectedCardsContainer, currentIndex);
                        }

                        if (computerCardEliminated) {
                            createCardEliminationAnimation(computerCard, computerSelectedCardsContainer, currentIndex);
                        }

                        // Schedule next battle or final transition
                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished(e -> run());
                        pause.play();
                    });

                    cardBattleTimeline.getKeyFrames().addAll(playerAttack, computerAttack, resetPlayerCard, resetComputerCard, clashEffect);
                    cardBattleTimeline.play();
                } else {
                    PauseTransition finalPause = new PauseTransition(Duration.seconds(1));
                    finalPause.setOnFinished(event -> onComplete.run());
                    finalPause.play();
                }
            }

            private void finishBattle() {
                PauseTransition finalPause = new PauseTransition(Duration.seconds(1));
                finalPause.setOnFinished(event -> onComplete.run());
                finalPause.play();
            }
        };

        // Start the first battle
        performCardBattle.run();
    }

    // Yeni kart eleme animasyonu - konumu korur
    private void createCardEliminationAnimation(Node card, HBox container, int index) {
        // Kartın mevcut konumunu koruyarak şeffaflığını azalt
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), card);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Kartı yok et ama yerini boş bırak
        fadeOut.setOnFinished(event -> {
            Platform.runLater(() -> {
                // Kartı şeffaf ve etkisiz hale getir
                card.setOpacity(0);
                card.setMouseTransparent(true);

                // Konteynerdeki diğer kartların yerini değiştirme
                container.getChildren().set(index, createPlaceholderNode());
            });
        });

        fadeOut.play();
    }

    // Boş kart için yer tutucu oluştur
    private Node createPlaceholderNode() {
        Pane placeholderPane = new Pane();
        placeholderPane.setMinSize(120, 180);  // Orijinal kart boyutu
        placeholderPane.setMaxSize(120, 180);
        placeholderPane.setStyle("-fx-background-color: transparent;");
        return placeholderPane;
    }

    // Güvenli kart eleme kontrolü (önceki örnekteki gibi)
    private boolean safelyCheckCardElimination(int cardIndex, boolean isPlayerCard) {
        try {
            List<Savas_Araclari> playerCards = instance.getInsanSeckart();
            List<Savas_Araclari> computerCards = instance.getPcSeckart();

            if (playerCards == null || computerCards == null ||
                    cardIndex >= playerCards.size() || cardIndex >= computerCards.size()) {
                return false;
            }

            if (isPlayerCard) {
                return playerCards.get(cardIndex).getDayaniklilik() <= 0;
            } else {
                return computerCards.get(cardIndex).getDayaniklilik() <= 0;
            }
        } catch (Exception e) {
            System.err.println("Kart eleme kontrolünde hata: " + e.getMessage());
            return false;
        }
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
            System.out.println("tur"+countRound+ "  "+instance.getTurncount());
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
        return instance.getController().getTurncount()==countRound || kontrol == 3 || kontrol == 4 || kontrol == 5 || kontrol == 6 || kontrol == 2;
    }

    private void handleGameOver() {
        instance.getController().getOyuncu().savasSonucu(
                instance.getController().getOyuncu(),
                instance.getController().getPc());

        finishButton.setDisable(true);

        // Oyun sonuç bilgilerini hazırla
        String gameOutcome = determineGameOutcome();

        // JavaFX sahnesini değiştir ve sonuç ekranını göster
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graphics/FXMLs/game_results.fxml"));
                Parent root = loader.load();
                GameResultsController controller = loader.getController();

                controller.setGameResults(
                        instance.getController().getOyuncu().getInsanSkor(),
                        instance.getController().getPc().getPcSkor(),
                        gameOutcome
                );

                Stage stage = (Stage) finishButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Oyun sonucunu belirleyen yardımcı metod
    private String determineGameOutcome() {
        int playerScore = instance.getController().getOyuncu().getInsanSkor();
        int computerScore = instance.getController().getPc().getPcSkor();

        if (playerScore > computerScore) return "win";
        if (playerScore < computerScore) return "lose";
        return "draw";
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
        alert.show();
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
        for (Savas_Araclari card : computerCards) {
            StackPane cardPane = createCardPane(null, true);
            // Set fixed size similar to player cards
            cardPane.setMinSize(120, 180);
            cardPane.setMaxSize(120, 180);

            // Update ImageView inside the cardPane
            ImageView cardView = (ImageView) cardPane.getChildren().get(0);
            cardView.setFitHeight(230);
            cardView.setFitWidth(180);
            cardView.setPreserveRatio(true);

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
            Oyuncu.setSelect(0);
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
            return new Image(Objects.requireNonNull(CardImageHelper.class.getResourceAsStream("/Graphics/Photos/KartArkasi.png")));
        }

        try {
            CardTypes cardType = CardTypes.fromString(sinif);
            return new Image(Objects.requireNonNull(CardImageHelper.class.getResourceAsStream(cardType.getImagePath())));
        } catch (Exception e) {
            System.err.println("Kart resmi yüklenemedi: " + e.getMessage());
            // Yedek olarak varsayılan kart görselini döndür
            return new Image(Objects.requireNonNull(CardImageHelper.class.getResourceAsStream("/Graphics/Photos/KartArkasi.png")));
        }
    }
}

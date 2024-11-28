package sample;

import Game.Dosya_Islemleri;
import Game.Oyuncu;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class UIController implements Initializable, Dosya_Islemleri {
    @FXML private HBox computerCardsContainer;
    @FXML private HBox computerSelectedCardsContainer;
    @FXML private HBox playerCardsContainer;
    @FXML private HBox playerSelectedCardsContainer;
    @FXML private Button finishButton;
    private Controller instance;
    private  int countRound=1;
    private int kontrol=0;
    private int selectedCardIndex = 0;
    @FXML
    private Label playerScoreLabel;
    @FXML
    private Label computerScoreLabel;
    public void setInstance(Controller instance) {
        this.instance = instance;
    }

    private Oyuncu playerOyuncu;
    private Oyuncu pcOyuncu;
    private Map<ImageView, ImageView> selectedToOriginalMap = new HashMap<>();
    private  int  selectedCardCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   //     playerScoreLabel.setText("Oyuncu Skoru: 0");
     //   computerScoreLabel.setText("Bilgisayar Skoru: 0");
        System.out.println("UIController initialized");
        assert computerCardsContainer != null : "computerCardsContainer FXML yüklenemedi";
        assert playerCardsContainer != null : "playerCardsContainer FXML yüklenemedi";
        assert computerSelectedCardsContainer != null : "computerSelectedCardsContainer FXML yüklenemedi";
        assert playerSelectedCardsContainer != null : "playerSelectedCardsContainer FXML yüklenemedi";
        if (finishButton != null) {
            finishButton.setDisable(true);
            finishButton.setOnAction(e -> {
                // Önce kart savaşlarını gerçekleştir
                Oyuncu.kartSavaslari(instance.getController().getOyuncu(),
                        instance.getController().getPc(),
                        instance.getInsanSeckart(),
                        instance.getPcSeckart());

                // Savaş sonrası yapılacak işlemler
                Platform.runLater(() -> {
                    if(kontrol!=7 && kontrol!=8){
                        kontrol=Game.Oyuncu.savasSonuclari(instance.getController().getOyuncu(), instance.getController().getPc(), countRound, 0);
                    }else{
                         kontrol=Game.Oyuncu.savasSonuclari(instance.getController().getOyuncu(), instance.getController().getPc(), countRound, 1);
                    }
                    instance.getController().getOyuncu().destendekiKartlar(instance.getController().getOyuncu(), instance.getController().getPc(), instance.getController().getOyuncu().getInsanKart(), instance.getController().getPc().getBilgisayarKart());
                    if(kontrol == 3 || kontrol == 4 || kontrol == 5 || kontrol == 6 || kontrol == 2)
                    {
                        instance.getController().getOyuncu().savasSonucu(instance.getController().getOyuncu(), instance.getController().getPc());
                        finishButton.setDisable(true);  // Butonu devre dışı bırak
                        showGameOverDialog();
                        return;
                    }
                    instance.getController().getPcSeckart().clear();
                    instance.getController().getInsanSeckart().clear();
                    selectedCardCount=0;
                    updateGameState();
                    setGameLists(playerOyuncu, pcOyuncu);
                    countRound++;
                });
            });        }
    }
    private void showGameOverDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oyun Bitti");
        alert.setHeaderText("Tüm turlar tamamlandı!");

        String message = String.format(
                "Oyun bitti!\nOyuncu Skoru: %d\nBilgisayar Skoru: %d",
                instance.getController().getOyuncu().getInsanSkor(),
                instance.getController().getPc().getPcSkor()
        );
        alert.setContentText(message);

        alert.setOnHidden(e -> {
            Platform.runLater(() -> {
                finishButton.getScene().getWindow().hide();
            });
        });

        alert.showAndWait();
    }


    private void createPlayerCards() {
        playerCardsContainer.getChildren().clear();
        ArrayList<Savas_Araclari> playerCards = playerOyuncu.getInsanKart();

        for (Savas_Araclari card : playerCards) {
            StackPane cardPane = createCardPane(card, false);
            playerCardsContainer.getChildren().add(cardPane);

            cardPane.setOnMouseClicked(event -> handleCardClick(cardPane, card));
        }
    }

    private void handleCardClick(StackPane cardPane, Savas_Araclari card) {
        if (selectedCardCount!=3){
            finishButton.setDisable(true);
        }
        if (playerOyuncu.getKullanilmisKartlarInsan().contains(card)
                && playerOyuncu.getKullanilmisKartlarInsan().size() != instance.getOyuncu().getInsanKart().size()
                && !playerSelectedCardsContainer.getChildren().contains(cardPane)) {
            System.out.println("Bu kart daha önce kullanılmış. Başka bir kart seçin!");
            return;
        }

        // Initially disable the finish button

        if (playerSelectedCardsContainer.getChildren().contains(cardPane)) {
            playerSelectedCardsContainer.getChildren().remove(cardPane);
            playerCardsContainer.getChildren().add(cardPane);
            cardPane.setOpacity(1.0);
            selectedCardCount--;

            // Kartı kullanılmış kartlardan ve seçilen kartlardan çıkar
            playerOyuncu.getKullanilmisKartlarInsan().remove(card);
            instance.getController().getInsanSeckart().remove(card);
            selectedCardIndex--;
            if (selectedCardCount!=3){
                finishButton.setDisable(true);
            }

        } else if (selectedCardCount < 3) {
            if (playerCardsContainer.getChildren().contains(cardPane)) {
                playerCardsContainer.getChildren().remove(cardPane);
                playerSelectedCardsContainer.getChildren().add(cardPane);
                instance.getInsanSeckart().add(card);

                // Kart seçme işlemini yap, güncel selectedCardIndex'i kullan
                Oyuncu.secilenKartlar(
                        instance.getController().getOyuncu(),
                        instance.getController().getPc(),
                        instance.getInsanSeckart(),
                        instance.getPcSeckart(),
                        selectedCardIndex
                );

                cardPane.setOpacity(0.7);
                if (selectedCardCount < 3) {
                    selectedCardIndex++;
                    selectedCardCount++;
                }
                if (selectedCardCount!=3){
                    finishButton.setDisable(true);
                }
                // Enable finish button only when 3 cards are selected
                if (selectedCardCount == 3) {
                    finishButton.setDisable(false);

                    Platform.runLater(() -> {
                        instance.getController().getOyuncu().savas(
                                instance.getController().getOyuncu(),
                                instance.getController().getPc(),
                                instance.getController().getInsanSeckart(),
                                instance.getController().getPcSeckart(),
                                countRound
                        );
                    });
                }
            }
        } else {
            System.out.println("Maksimum 3 kart seçebilirsiniz!");
        }
    }
    public void setGameLists(Oyuncu playerOyuncu, Oyuncu pcOyuncu) {
        if (playerOyuncu == null || pcOyuncu == null) {
            throw new IllegalArgumentException("Oyuncu nesneleri null olamaz");
        }

        this.playerOyuncu = playerOyuncu;
        this.pcOyuncu = pcOyuncu;

        Platform.runLater(() -> {
            playerScoreLabel.setText("Oyuncu Skoru: " + playerOyuncu.getInsanSkor());
            computerScoreLabel.setText("Bilgisayar Skoru: " + pcOyuncu.getPcSkor());
            createPlayerCards();
            createComputerCards();
        });
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
            // Health Circle
            Circle healthCircle = new Circle(15); // 15 pixel radius
            healthCircle.setFill(Color.RED);

            // Health Label inside the circle
            Label healthLabel = new Label(String.valueOf(card.getDayaniklilik()));
            healthLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

            // StackPane to center the label in the circle
            StackPane healthIndicator = new StackPane(healthCircle, healthLabel);

            VBox details = new VBox(3);
            details.setAlignment(Pos.BOTTOM_CENTER);

           // Label typeLabel = new Label(card.getSinif());
          //  Label strengthLabel = new Label("Güç: " + card.getVurus());
           // Label healthDetailsLabel = new Label("Can: " + card.getDayaniklilik());

         //   typeLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
           // strengthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 9px;");
            //healthDetailsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 9px;");

           // details.getChildren().addAll(typeLabel, strengthLabel, healthDetailsLabel);

            // Hover Effects
            cardView.setOnMouseEntered(e -> cardPane.setEffect(new DropShadow(10, Color.YELLOW)));
            cardView.setOnMouseExited(e -> cardPane.setEffect(null));

            // Create a VBox to stack the health indicator above the card
            VBox cardLayout = new VBox(5); // 5 is the spacing between health indicator and card
            cardLayout.setAlignment(Pos.CENTER);
            cardLayout.getChildren().addAll(healthIndicator, cardView, details);

            cardPane.getChildren().add(cardLayout);
        } else {
            cardPane.getChildren().add(cardView);
        }

        return cardPane;
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
            playerScoreLabel.setText("Oyuncu Skoru: " + playerOyuncu.getInsanSkor());
            computerScoreLabel.setText("Bilgisayar Skoru: " + pcOyuncu.getPcSkor());
        });
    }
    private void animateHealthIndicator(Label healthIndicator) {
        // Create a timeline to make the health indicator blink
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, evt -> healthIndicator.setVisible(true)),
                new KeyFrame(Duration.seconds(0.5), evt -> healthIndicator.setVisible(false)),
                new KeyFrame(Duration.seconds(1), evt -> healthIndicator.setVisible(true))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
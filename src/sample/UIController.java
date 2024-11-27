package sample;

import Game.Dosya_Islemleri;
import Game.Oyuncu;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.fxml.Initializable;
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

    public void setInstance(Controller instance) {
        this.instance = instance;
    }

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
            finishButton.setOnAction(e -> {
                // Önce kart savaşlarını gerçekleştir
                Oyuncu.kartSavaslari(instance.getController().getOyuncu(),
                        instance.getController().getPc(),
                        instance.getInsanSeckart(),
                        instance.getPcSeckart());

                // Savaş sonrası yapılacak işlemler
                Platform.runLater(() -> {
                    instance.getController().getOyuncu().savas(instance.getController().getOyuncu(), instance.getController().getPc(), instance.getController().getInsanSeckart(), instance.getController().getPcSeckart(), 1);
//       Game.Oyuncu.kartSavaslari(oyuncu, pc, insanSeckart, pcSeckart);
                    int a = Game.Oyuncu.savasSonuclari(instance.getController().getOyuncu(), instance.getController().getPc(), 1, 0);
                    instance.getController().getOyuncu().destendekiKartlar(instance.getController().getOyuncu(), instance.getController().getPc(), instance.getController().getOyuncu().getInsanKart(), instance.getController().getPc().getBilgisayarKart());

                    instance.getController().getPcSeckart().clear();
                    instance.getController().getInsanSeckart().clear();
                });
            });        }
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
        if (playerOyuncu.getKullanilmisKartlarInsan().contains(card)) {
            // Uyarı ver ve seçim işlemini durdur
            System.out.println("Bu kart daha önce kullanılmış. Başka bir kart seçin!");
            return;
        }

        if (playerSelectedCardsContainer.getChildren().contains(cardPane)) {
            // Kart seçili alandaysa geri taşı
            playerSelectedCardsContainer.getChildren().remove(cardPane);
            playerCardsContainer.getChildren().add(cardPane);
            cardPane.setOpacity(1.0); // Görünürlüğü normale çevir
            selectedCardCount--;
            playerOyuncu.getKullanilmisKartlarInsan().remove(card); // Listeden çıkar
        } else if (selectedCardCount < 3) {
            if (playerCardsContainer.getChildren().contains(cardPane)) {
                // Kart seçili değilse seçili alana taşı
                playerCardsContainer.getChildren().remove(cardPane);
                playerSelectedCardsContainer.getChildren().add(cardPane);
                instance.getInsanSeckart().add(card);
                Oyuncu.secilenKartlar(instance.getController().getOyuncu(),instance.getController().getPc(),instance.getInsanSeckart(),instance.getPcSeckart());
                System.out.println("evet:"+ instance.getInsanSeckart());
                cardPane.setOpacity(0.7); // Hafif seçili efekti
                selectedCardCount++;
                playerOyuncu.getKullanilmisKartlarInsan().add(card); // Kullanılmış kartlara ekle
            }
        } else {
            // Maksimum kart seçildiğinde kullanıcıyı bilgilendir
            System.out.println("Maksimum 3 kart seçebilirsiniz!");
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
            String imagePath = isComputer ? "/sample/arka1.png" : "/sample/card3.png";
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            cardView.setImage(image);
        } catch (Exception e) {
            System.err.println("Kart resmi yüklenemedi: " + e.getMessage());
        }

        if (!isComputer && card != null) {
            VBox details = new VBox(3);
            details.setAlignment(Pos.BOTTOM_CENTER);

            Label typeLabel = new Label(card.getSinif());
            Label strengthLabel = new Label("Güç: " + card.getVurus());
            Label healthLabel = new Label("Can: " + card.getDayaniklilik());

            typeLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
            strengthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 9px;");
            healthLabel.setStyle("-fx-text-fill: white; -fx-font-size: 9px;");

            details.getChildren().addAll(typeLabel, strengthLabel, healthLabel);
            cardPane.getChildren().addAll(cardView, details);

            // Hover Effects
            cardView.setOnMouseEntered(e -> cardPane.setEffect(new DropShadow(10, Color.YELLOW)));
            cardView.setOnMouseExited(e -> cardPane.setEffect(null));
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
            selectedToOriginalMap.clear();
            playerSelectedCardsContainer.getChildren().clear();
            computerSelectedCardsContainer.getChildren().clear();
        });
    }
}
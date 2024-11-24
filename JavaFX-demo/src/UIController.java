import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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

    private Map<ImageView, ImageView> selectedToOriginalMap = new HashMap<>();
    private int selectedCardCount = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCardImages();
        setupCardClickEvents();
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
        playerCard1.setOnMouseClicked(event -> handleCardSelection(playerCard1));
        playerCard2.setOnMouseClicked(event -> handleCardSelection(playerCard2));
        playerCard3.setOnMouseClicked(event -> handleCardSelection(playerCard3));
        playerCard4.setOnMouseClicked(event -> handleCardSelection(playerCard4));
        playerCard5.setOnMouseClicked(event -> handleCardSelection(playerCard5));
        playerCard6.setOnMouseClicked(event -> handleCardSelection(playerCard6));

        // Seçili kartlar için tıklama olayları
        playerSelectedCard1.setOnMouseClicked(event -> returnCardToOriginal(playerSelectedCard1, 0));
        playerSelectedCard2.setOnMouseClicked(event -> returnCardToOriginal(playerSelectedCard2, 1));
        playerSelectedCard3.setOnMouseClicked(event -> returnCardToOriginal(playerSelectedCard3, 2));
    }

    private void handleCardSelection(ImageView sourceCard) {
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

}
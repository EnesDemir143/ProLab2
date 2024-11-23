package sample;

import Game.Oyuncu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private TextField username;
    @FXML
    private TextField level;

    private Oyuncu oyuncu;

    @FXML
    public void initialize() {
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file";
        assert level != null : "fx:id=\"level\" was not injected: check your FXML file";

        // Level kısmını boş başlat
        level.setText("");

        // Enter tuşuna basıldığında kontrol
        level.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSubmit();

                // Pencereyi kapat
                Stage stage = (Stage) level.getScene().getWindow();
                Platform.runLater(() -> {
                    stage.close();
                });
            }
        });
    }

    public void setPerson(Oyuncu oyuncu) {
        if (oyuncu != null && username != null && level != null) {
            this.oyuncu = oyuncu;
            username.setText(oyuncu.getOyuncu_adi());
            // Level'ı boş bırak
            level.setText("");
        } else {
            System.err.println("Oyuncu veya UI elemanları null!");
        }
    }

    @FXML
    private void handleSubmit() {
        if (oyuncu == null || username == null || level == null) {
            System.err.println("Gerekli alanlar başlatılmamış!");
            return;
        }

        try {
            String textInput = username.getText();
            int intLevel = Integer.parseInt(level.getText());

            oyuncu.setOyuncu_adi(textInput);
            oyuncu.setInsanSkor(intLevel);

            System.out.println("Oyuncu Adı: " + oyuncu.getOyuncu_adi());
            System.out.println("Oyuncu Skoru: " + oyuncu.getInsanSkor());

        } catch (NumberFormatException e) {
            System.err.println("Lütfen level için geçerli bir sayı girin!");
        }
    }

    public TextField getLevel() {
        return level;
    }

    public TextField getUsername() {
        return username;
    }
}
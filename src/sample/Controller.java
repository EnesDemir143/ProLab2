package sample;

import Game.Oyuncu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private TextField username;
    @FXML
    private TextField level;

    private Oyuncu oyuncu;

    @FXML
    public void initialize() {
        // FXML öğelerinin doğru bağlandığını kontrol et
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file.";
        assert level != null : "fx:id=\"level\" was not injected: check your FXML file.";

        // Level alanını başlangıçta boş bırak
        level.setText("");

        // username için Enter ve yön tuşları ayarları
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                level.requestFocus(); // Enter veya aşağı ok tuşuna basıldığında level alanına geç
            }
        });

        // level için Enter ve yön tuşları ayarları
        level.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSubmit(); // Giriş tamamlanır
                closeWindow();  // Pencereyi kapatır
            } else if (event.getCode() == KeyCode.UP) {
                username.requestFocus(); // Yukarı ok tuşuna basıldığında username alanına geri dön
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
    private void closeWindow() {
        Stage stage = (Stage) level.getScene().getWindow();
        Platform.runLater(stage::close);
    }
    private void handleKeyPress(KeyEvent event, TextField nextField) {
        if (event.getCode() == KeyCode.ENTER) {
            nextField.requestFocus(); // Odak diğer TextField'a geçer
        } else if (event.getCode() == KeyCode.DOWN) {
            nextField.requestFocus(); // Aşağı tuşu ile geçiş
        } else if (event.getCode() == KeyCode.UP) {
            nextField.requestFocus(); // Yukarı tuşu ile geçiş
        }
    }
    public TextField getLevel() {
        return level;
    }

    public TextField getUsername() {
        return username;
    }
}
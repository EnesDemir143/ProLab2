package sample;

import Game.Oyuncu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private TextField username;
    @FXML
    private TextField level;

    private Stage stage;
    private Oyuncu oyuncu;

    public void setStageAndOyuncu(Stage stage, Oyuncu oyuncu) {
        this.stage = stage;
        this.oyuncu = oyuncu;
    }

    @FXML
    public void initialize() {
        if (username != null && level != null) {
            setupSampleScene();
        }
    }

    private void setupSampleScene() {
        // FXML öğelerinin doğru bağlandığını kontrol et
        assert username != null : "fx:id=\"username\" was not injected: check your FXML file.";
        assert level != null : "fx:id=\"level\" was not injected: check your FXML file.";

        level.setText("");

        // username için Enter ve yön tuşları ayarları
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                level.requestFocus();
            }
        });

        // level için Enter ve yön tuşları ayarları
        level.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleSubmit();
            } else if (event.getCode() == KeyCode.UP) {
                username.requestFocus();
            }
        });
    }

    @FXML
    private void handleSubmit() {
        if (!validateInput()) {
            return; // Eğer giriş doğrulaması başarısızsa işlemi durdur
        }

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

            closeWindow(); // Doğru giriş yapıldığında pencereyi kapat
        } catch (NumberFormatException e) {
            System.err.println("Lütfen level için geçerli bir sayı girin!");
            showError("Lütfen level için geçerli bir sayı girin!");
        }
    }

    @FXML
    private void switchToSampleScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Scene sampleScene = new Scene(loader.load());

            // Yeni sahne için controller ayarları
            Controller sampleController = loader.getController();
            sampleController.setStageAndOyuncu(stage, oyuncu);

            stage.setScene(sampleScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showAlertError() {
        // Hangi TextField'in boş olduğunu kontrol et
        if (username == null || username.getText().trim().isEmpty()) {
            showError("Kullanıcı adı boş olamaz!");
        } else if (level == null || level.getText().trim().isEmpty()) {
            showError("Seviye boş olamaz!");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hata");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean validateInput() {
        // Kullanıcı adı ve seviye alanlarının doluluğunu kontrol et
        if (username.getText().trim().isEmpty()) {
            showError("Kullanıcı adı boş olamaz!");
            clearFields();  // Boş alan varsa text field'leri temizle
            username.requestFocus();  // Kullanıcıyı username alanına yönlendir
            return false;
        }

        if (level.getText().trim().isEmpty()) {
            showError("Seviye boş olamaz!");
            clearFields();  // Boş alan varsa text field'leri temizle
            level.requestFocus();  // Kullanıcıyı level alanına yönlendir
            return false;
        }

        return true;
    }

    private void clearFields() {
        // TextField'leri temizler
        username.clear();
        level.clear();
    }

    @FXML
    private void closeWindow() {
        if (stage != null) {
            stage.close();
        }
    }
}
package sample;

import Game.Oyuncu;
import Veri_Modelleri.Savas_Araclari_Modeli.Savas_Araclari;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Controller {
    @FXML private TextField username;
    @FXML private TextField level;
    private Stage stage;
    private Oyuncu oyuncu;
    private Oyuncu pc;

    public void setStageAndOyuncu(Stage stage, Oyuncu oyuncu) {
        this.stage = stage;
        this.oyuncu = oyuncu;
        this.pc = new Oyuncu();
        initialize();
    }

    @FXML
    public void initialize() {
        if (username != null && level != null) {
            setupSampleScene();
        }
    }

    private void setupSampleScene() {
        username.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                level.requestFocus();
            }
        });

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
            return;
        }

        try {
            oyuncu.setOyuncu_adi(username.getText());
            oyuncu.setInsanSkor(Integer.parseInt(level.getText()));
            switchToThirdScene();
        } catch (Exception e) {
            showError("Geçersiz giriş! Lütfen kontrol ediniz.");
        }
    }

    private void switchToThirdScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/sample3.fxml"));
            Scene thirdScene = new Scene(loader.load());
            UIController gameController = loader.getController();

            // Oyun hazırlıklarını arka planda yap
            Task<Void> gameSetupTask = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    oyuncu.dosyayiSifirla();
                    oyuncu.insanKartListesi(oyuncu);
                    pc.bilgisyarKartListesi(pc);
                    oyuncu.ilkKartlar(oyuncu, pc, oyuncu.getInsanKart(), pc.getBilgisayarKart());
                    return null;
                }
            };

            gameSetupTask.setOnSucceeded(event -> {
                Platform.runLater(() -> {
                    try {
                        gameController.setGameLists(oyuncu, pc);
                        stage.setScene(thirdScene);
                        startGame();
                    } catch (Exception e) {
                        showError("Oyun başlatılırken hata oluştu: " + e.getMessage());
                    }
                });
            });

            gameSetupTask.setOnFailed(event -> {
                showError("Oyun hazırlıkları sırasında hata oluştu: " + gameSetupTask.getException().getMessage());
            });

            // Arka plan işlemini başlat
            new Thread(gameSetupTask).start();

        } catch (Exception e) {
            showError("Oyun yüklenirken hata oluştu: " + e.getMessage());
        }
    }

    private void startGame() {
        // Oyun mantığını arka planda çalıştır
        Task<Void> gameTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                real();
                return null;
            }
        };

        gameTask.setOnFailed(event -> {
            showError("Oyun sırasında hata oluştu: " + gameTask.getException().getMessage());
        });

        // Arka plan işlemini başlat
        new Thread(gameTask).start();
    }

    public void real() {
        ArrayList<Savas_Araclari> pcSeckart = new ArrayList<>();
        ArrayList<Savas_Araclari> insanSeckart = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            Platform.runLater(() -> {
                // UI güncellemeleri buraya
            });

            Game.Oyuncu.secilenKartlar(oyuncu, pc, insanSeckart, pcSeckart);
            oyuncu.savas(oyuncu, pc, insanSeckart, pcSeckart, i);
            Game.Oyuncu.kartSavaslari(oyuncu, pc, insanSeckart, pcSeckart);
            int a = Game.Oyuncu.savasSonuclari(oyuncu, pc, i, 0);
            oyuncu.destendekiKartlar(oyuncu, pc, oyuncu.getInsanKart(), pc.getBilgisayarKart());

            pcSeckart.clear();
            insanSeckart.clear();

            if (a == 7 || a == 8) {
                Game.Oyuncu.secilenKartlar(oyuncu, pc, insanSeckart, pcSeckart);
                oyuncu.savas(oyuncu, pc, insanSeckart, pcSeckart, i + 1);
                Game.Oyuncu.kartSavaslari(oyuncu, pc, insanSeckart, pcSeckart);
                Game.Oyuncu.savasSonuclari(oyuncu, pc, i + 1, 1);
                oyuncu.destendekiKartlar(oyuncu, pc, oyuncu.getInsanKart(), pc.getBilgisayarKart());
                oyuncu.savasSonucu(oyuncu, pc);
                break;
            }
            if (a == 3 || a == 4 || a == 5 || a == 6 || a == 2) {
                oyuncu.savasSonucu(oyuncu, pc);
                break;
            }
        }
    }

    private void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private boolean validateInput() {
        if (username.getText().trim().isEmpty()) {
            showError("Kullanıcı adı boş olamaz!");
            return false;
        }

        if (level.getText().trim().isEmpty()) {
            showError("Seviye boş olamaz!");
            return false;
        }

        try {
            Integer.parseInt(level.getText());
        } catch (NumberFormatException e) {
            showError("Seviye sayısal bir değer olmalıdır!");
            return false;
        }

        return true;
    }
}
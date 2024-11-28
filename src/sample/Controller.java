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
    @FXML private TextField turCount;

    private Controller controller=this;

    public ArrayList<Savas_Araclari> getPcSeckart() {
        return pcSeckart;
    }

    public Oyuncu getPc() {
        return pc;
    }

    public Oyuncu getOyuncu() {
        return oyuncu;
    }

    public Controller getController() {
        return controller;
    }

    public TextField getTurCount() {
        return turCount;
    }

    private ArrayList<Savas_Araclari> pcSeckart = new ArrayList<>();
    private ArrayList<Savas_Araclari> insanSeckart = new ArrayList<>();

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
            } else if (event.getCode() == KeyCode.UP) {
                turCount.requestFocus();
            }
        });

        level.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                turCount.requestFocus();
            } else if (event.getCode() == KeyCode.UP) {
                username.requestFocus();
            }
        });

        turCount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (validateAllFields()) {
                    handleSubmit();
                }
            } else if (event.getCode() == KeyCode.DOWN) {
                username.requestFocus();
            } else if (event.getCode() == KeyCode.UP) {
                level.requestFocus();
            }
        });
    }

    @FXML
    private void handleSubmit() {
        try {
            oyuncu.setOyuncu_adi(username.getText());
            oyuncu.setInsanSkor(Integer.parseInt(level.getText()));
            // Save turn count to wherever you need it in your game logic
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
            gameController.setInstance(this);
            oyuncu.setController(this);
            pc.setController(this);
            // Oyun hazırlıklarını arka planda yap
            Task<Void> gameSetupTask = new Task<>() {
                @Override
                protected Void call()  {
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
            protected Void call()  {
                return null;
            }
        };

        gameTask.setOnFailed(event -> {
            showError("Oyun sırasında hata oluştu: " + gameTask.getException().getMessage());
        });

        // Arka plan işlemini başlat
        new Thread(gameTask).start();
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

    private boolean validateAllFields() {
        // Username validation
        if (username.getText().trim().isEmpty()) {
            showError("Kullanıcı adı boş olamaz!");
            return false;
        }

        // Level validation
        if (level.getText().trim().isEmpty()) {
            showError("Seviye boş olamaz!");
            return false;
        }
        try {
            int levelValue = Integer.parseInt(level.getText());
            if (levelValue < 0) {
                showError("Seviye pozitif bir değer olmalıdır!");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Seviye sayısal bir değer olmalıdır!");
            return false;
        }

        // Turn count validation
        if (turCount.getText().trim().isEmpty()) {
            showError("Tur sayısı boş olamaz!");
            return false;
        }
        try {
            int turns = Integer.parseInt(turCount.getText());
            if (turns < 1 || turns > 15) {
                showError("Tur sayısı 1 ile 15 arasında olmalıdır!");
                return false;
            }
        } catch (NumberFormatException e) {
            showError("Tur sayısı sayısal bir değer olmalıdır!");
            return false;
        }

        return true;
    }

    public ArrayList<Savas_Araclari> getInsanSeckart() {
        return insanSeckart;
    }
}
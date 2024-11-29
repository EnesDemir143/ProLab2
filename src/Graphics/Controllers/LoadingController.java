package Graphics.Controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadingController {
    @FXML private ProgressIndicator progressIndicator;
    @FXML private Text loadingText;

    private Controller parentController;
    private Stage stage;

    public void setParentController(Controller controller, Stage stage) {
        this.parentController = controller;
        this.stage = stage;
        startLoading();
    }

    private void startLoading() {
        Task<Void> loadingTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Oyun hazırlık adımlarını simüle et
                for (int i = 0; i <= 100; i++) {
                    final double progress = i / 100.0;
                    Platform.runLater(() -> {
                        progressIndicator.setProgress(progress);
                        updateLoadingText(progress);
                    });

                    // Oyun hazırlıklarını burada yap
                    if (i == 50) {
                        parentController.getOyuncu().setController(parentController);
                        parentController.getOyuncu().dosyayiSifirla();
                        parentController.getOyuncu().insanKartListesi(parentController.getOyuncu());
                        parentController.getPc().bilgisyarKartListesi(parentController.getPc());
                        parentController.getOyuncu().ilkKartlar(parentController.getOyuncu(), parentController.getPc(),
                                parentController.getOyuncu().getInsanKart(),
                                parentController.getPc().getBilgisayarKart());
                    }

                    Thread.sleep(50); // Simüle edilmiş yükleme süresi
                }
                return null;
            }
        };

        loadingTask.setOnSucceeded(event -> {
            Platform.runLater(() -> {
                try {
                    // Üçüncü sahneye (oyun sahnesine) geç
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graphics/FXMLs/sample3.fxml"));
                    Scene thirdScene = new Scene(loader.load());

                    UIController gameController = loader.getController();
                    gameController.setInstance(parentController);
                    gameController.setGameLists(parentController.getOyuncu(), parentController.getPc());

                    stage.setScene(thirdScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        new Thread(loadingTask).start();
    }

    private void updateLoadingText(double progress) {
        if (progress < 0.2) {
            loadingText.setText("Oyun hazırlanıyor...");
        } else if (progress < 0.4) {
            loadingText.setText("Kartlar dağıtılıyor...");
        } else if (progress < 0.6) {
            loadingText.setText("Oyuncular hazırlanıyor...");
        } else if (progress < 0.8) {
            loadingText.setText("Son hazırlıklar...");
        } else {
            loadingText.setText("Oyun başlıyor...");
        }
    }
}
package Graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // İlk olarak sample2.fxml'i yükle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Graphics/FXMLs/sample2.fxml"));
        Scene scene = new Scene(loader.load());

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
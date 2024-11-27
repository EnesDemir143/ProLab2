package sample;

import Game.Oyuncu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/sample.fxml"));
        Scene scene = new Scene(loader.load());

        Controller controller = loader.getController();
        Oyuncu oyuncu = new Oyuncu("", "", 0);
        controller.setStageAndOyuncu(primaryStage, oyuncu);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

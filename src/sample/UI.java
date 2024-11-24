package sample;

import Game.Oyuncu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {
    private static Oyuncu initialOyuncu;

    public static void setInitialOyuncu(Oyuncu oyuncu) {
        initialOyuncu = oyuncu;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample2.fxml"));
        Scene scene = new Scene(loader.load());

        // Controller'a stage ve oyuncuyu ilet
        Controller controller = loader.getController();
        controller.setStageAndOyuncu(primaryStage, initialOyuncu);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Sample 2");
        primaryStage.show();
    }
}
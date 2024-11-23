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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(loader.load());

        Controller controller = loader.getController();
        controller.setPerson(initialOyuncu);

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("String Girişi (Main): " + controller.getUsername().getText());
            System.out.println("Double Girişi (Main): " + controller.getLevel().getText());
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Uygulaması");
        primaryStage.show();
    }
}
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FXML dosyasını yükle
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UIsample.fxml")); // FXML dosyasını src dizininde bulabilmek için yol
        AnchorPane root = loader.load();

        // Sahneyi oluştur
        Scene scene = new Scene(root);

        // Başlık ayarla
        primaryStage.setTitle("2D Kart Oyunu");

        // Sahneyi ayarla
        primaryStage.setScene(scene);

        // Pencereyi göster
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Uygulama başlat
    }
}

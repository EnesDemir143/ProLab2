package Graphics.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameResultsController implements Initializable {
    @FXML private Label outcomeLabel;
    @FXML private Label playerScoreLabel;
    @FXML private Label computerScoreLabel;

    private int playerScore = 0;
    private int computerScore = 0;
    private String gameOutcome = "draw";

    public void setGameResults(int playerScore, int computerScore, String gameOutcome) {
        this.playerScore = playerScore;
        this.computerScore = computerScore;
        this.gameOutcome = gameOutcome != null ? gameOutcome : "draw";

        if (outcomeLabel != null) {
            updateUI();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUI();
    }

    private void updateUI() {
        // Set score labels
        playerScoreLabel.setText(String.format("Player: %d", playerScore));
        computerScoreLabel.setText(String.format("Computer: %d", computerScore));

        // Set outcome label and color
        switch (gameOutcome.toLowerCase()) {
            case "win":
                outcomeLabel.setText("Victory!");
                outcomeLabel.setTextFill(Color.LIGHTGREEN);
                break;
            case "lose":
                outcomeLabel.setText("Defeat!");
                outcomeLabel.setTextFill(Color.SALMON);
                break;
            default:
                outcomeLabel.setText("Draw!");
                outcomeLabel.setTextFill(Color.GOLD);
        }
    }

    @FXML
    private void exitGame() {
        Stage currentStage = (Stage) outcomeLabel.getScene().getWindow();
        currentStage.close();
    }
}
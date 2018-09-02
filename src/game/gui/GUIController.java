package game.gui;

import game.TicTacToe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.util.Arrays;

public class GUIController {
    @FXML
    public Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    public Text whosTurn;
    public Text playerPoints;
    public Text aiPoints;
    private boolean wait = false;
    private Button[] buttons;
    private TicTacToe mainGame = null;

    public void setMainGame(TicTacToe g) {
        this.mainGame = g;
    }

    public void initialize() {
        playerPoints.setText("0");
        aiPoints.setText("0");
        buttons = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
    }

    public void btnClick(ActionEvent actionEvent) {
        if (wait)
            return;
        wait = true;
        whosTurn.setText("AIs turn!");
        Button src = (Button) actionEvent.getSource();
        src.setDisable(true);
        src.setText("X");
        switch (mainGame.play(Arrays.asList(buttons).indexOf(src))) {
            case 1:
                won("X");
                break;
            case 2:
                whosTurn.setText("DRAW!");
                break;
            case 0:
                mainGame.aisTurn();
                break;
        }
    }

    public void won(String w) {
        if (w.equals("X")) {
            whosTurn.setText("YOU won!");
            playerPoints.setText((Integer.parseInt(playerPoints.getText()) + 1) + "");
        }
        if (w.equals("O")) {
            whosTurn.setText("AI won!");
            aiPoints.setText((Integer.parseInt(aiPoints.getText()) + 1) + "");
        }
        for (Button b : buttons)
            b.setDisable(true);
    }

    public void draw() {
        whosTurn.setText("DRAW!");
    }

    public void aiPlays(int b) {
        buttons[b].setText("O");
        buttons[b].setDisable(true);
        whosTurn.setText("YOUR turn!");
        wait = false;
    }

    public void btnReset(ActionEvent actionEvent) {
        for (Button b : buttons) {
            b.setDisable(false);
            b.setText("");
        }
        mainGame.reset();
        wait = false;
    }
}

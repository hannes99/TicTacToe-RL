package gui;

import game.Environment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.HashMap;

public class Controller {
    @FXML
    public Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    public Text whosTurn;
    public Text playerPoints;
    public Text aiPoints;
    private Button[] buttons;

    public void initialize() {
        playerPoints.setText("0");
        aiPoints.setText("0");
        buttons = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
    }

    public void btnClick(ActionEvent actionEvent) {
        Environment env = Environment.getInstance();
        Button src = (Button) actionEvent.getSource();
        src.setDisable(true);
        String currentSymbol = env.getCurrentPlayer().equals(Environment.X) ? "X" : "O";

        Integer[] status = env.play(Arrays.asList(buttons).indexOf(src), true);
        if (Environment.AI) {
            Button aiSel = buttons[status[1]];
            aiSel.setDisable(true);
            aiSel.setText(currentSymbol.equals("X") ? "O" : "X");
        }

        System.out.println("STATUS => " + status[0]);
        src.setText(currentSymbol);
        if (status[0].equals(2)) {
            whosTurn.setText("DRAW");
        } else if (status[0].equals(1)) {
            whosTurn.setText(currentSymbol + " WINS");
            Text t = currentSymbol.equals("X") ? playerPoints : aiPoints;
            t.setText("" + (Integer.parseInt(t.getText()) + 1));
            for (Button b : buttons)
                b.setDisable(true);

        } else {
            whosTurn.setText(env.getCurrentPlayer().equals(Environment.X) ? "X" : "O");
        }
    }

    public void btnReset(ActionEvent actionEvent) {
        for (Button b : buttons) {
            b.setDisable(false);
            b.setText("");
        }
        Environment.getInstance().reset();
        whosTurn.setText(Environment.getInstance().getCurrentPlayer().equals(Environment.X) ? "X" : "O");
    }
}

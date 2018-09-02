package game.gui;

import game.TicTacToe;
import game.TicTacToeAI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameGUI extends Application {

    private TicTacToe game;
    private GUIController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("base.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        game = new TicTacToe(controller);
        game.addAI(new TicTacToeAI(game));
        controller.setMainGame(game);
        primaryStage.setTitle("TicTacToe");
        primaryStage.minWidthProperty().setValue(320.0);
        primaryStage.minHeightProperty().setValue(400.0);
        primaryStage.setScene(new Scene(root, 500, 550));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

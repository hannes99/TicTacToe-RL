package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("base.fxml"));
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

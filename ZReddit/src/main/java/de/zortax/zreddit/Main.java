package de.zortax.zreddit;// Created by leo on 25.02.18

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main_window.fxml"));
        primaryStage.setTitle("ZReddit - v1.0-SNAPSHOT");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }
}

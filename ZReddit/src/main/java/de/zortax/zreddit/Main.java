package de.zortax.zreddit;// Created by leo on 25.02.18

import de.zortax.pra.network.messages.Dispatcher;
import de.zortax.pra.network.messages.impl.PropertiesAdapter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {

    public static final String ZREDDIT_VERSION = "v1.0-SNAPSHOT";

    private static Logger logger;
    private static Dispatcher dsp;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/properties/logging.properties"));
        logger = Logger.getLogger("debug-logger");
        PropertiesAdapter propertiesAdapter = new PropertiesAdapter();
        propertiesAdapter.initialize(logger);
        dsp = new Dispatcher(propertiesAdapter);

        logger.info(dsp.getMessage("default", "zreddit.debug.startup", ZREDDIT_VERSION));

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main_window.fxml"));
        primaryStage.setTitle("ZReddit - v1.0-SNAPSHOT");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.show();
    }
}

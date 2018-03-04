package de.zortax.zreddit;// Created by leo on 25.02.18

import de.zortax.pra.network.config.Config;
import de.zortax.pra.network.event.EventManager;
import de.zortax.pra.network.messages.Dispatcher;
import de.zortax.pra.network.messages.impl.PropertiesAdapter;
import de.zortax.zreddit.config.ClientConfig;
import de.zortax.zreddit.reddit.RedditManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ZReddit extends Application {

    public static final String ZREDDIT_VERSION = "v1.0-SNAPSHOT";

    private static Logger logger;
    private static Dispatcher dsp;
    private static ClientConfig config;
    private static EventManager eventManager;
    private static RedditManager redditManager;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        config = Config.load("config.json", ClientConfig.class);
        LogManager.getLogManager().readConfiguration(getClass().getResourceAsStream("/properties/logging.properties"));
        logger = Logger.getLogger("debug-logger");
        PropertiesAdapter propertiesAdapter = new PropertiesAdapter();
        propertiesAdapter.initialize(logger);
        dsp = new Dispatcher(propertiesAdapter);

        logger.info(dsp.getMessage(config.language, "zreddit.debug.startup", ZREDDIT_VERSION));

        eventManager = new EventManager();
        redditManager = new RedditManager();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main_window.fxml"));
        primaryStage.setTitle("ZReddit - v1.0-SNAPSHOT");
        primaryStage.setScene(new Scene(root, 1100, 700));
        primaryStage.setOnCloseRequest(event -> {
            config.save();
            System.exit(0);
        });
        primaryStage.show();

        redditManager.connect();

    }

    public static Logger getLogger() {
        return logger;
    }

    public static Dispatcher getDsp() {
        return dsp;
    }

    public static ClientConfig getConfig() {
        return config;
    }

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static RedditManager getRedditManager() {
        return redditManager;
    }

    public static String getMessage(String key, String... vals) {
        return dsp.getMessage(config.language, key, (Object[]) vals);
    }

}

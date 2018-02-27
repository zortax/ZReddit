package de.zortax.zreddit.controller.submission;// Created by leo on 27.02.18

import de.zortax.zreddit.Main;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public enum UISubmissionType {

    EMPTY(null),
    LINK("submission_link.fxml"),
    CITATION("submission_citated_text.fxml"),
    IMAGE("submission_image.fxml");

    private URL fxmlUrl;

    UISubmissionType(String fxml) {
        this.fxmlUrl = Main.class.getResource("/fxml/submission/" + fxml);
    }

    public <T>T createInstance() {
        try {
            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            loader.load();
            return loader.getController();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

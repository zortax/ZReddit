package de.zortax.zreddit.controller.submission;// Created by leo on 27.02.18

import de.zortax.zreddit.ZReddit;
import javafx.fxml.FXMLLoader;

import java.net.URL;

public class UISubmissionType {

    public static final UISubmissionType EMPTY = new UISubmissionType("");
    public static final UISubmissionType LINK = new UISubmissionType("submission_link.fxml");
    public static final UISubmissionType CITATION = new UISubmissionType("submission_citated_text.fxml");
    public static final UISubmissionType IMAGE = new UISubmissionType("submission_image.fxml");
    public static final UISubmissionType GIF = new UISubmissionType("submission_gif.fxml");
    public static final UISubmissionType YOUTUBE = new UISubmissionType("submission_youtube.fxml");

    private URL fxmlUrl;

    public UISubmissionType(String fxml) {
        this.fxmlUrl = ZReddit.class.getResource("/fxml/submission/" + fxml);
    }

    public UISubmissionType(URL fxmlUrl) {
        this.fxmlUrl = fxmlUrl;
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

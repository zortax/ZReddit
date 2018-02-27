package de.zortax.zreddit.controller.submission;// Created by leo on 27.02.18

import de.zortax.zreddit.utils.ImageUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import net.dean.jraw.models.Submission;

import java.io.InputStream;

public class ImageController implements SubmissionElement {

    @FXML public HBox box;
    @FXML public ImageView image;
    @FXML public Label label;

    @FXML
    public void initialize() {

    }


    @Override
    public void init(Submission submission) {
        String url = submission.getUrl();

        if (url.startsWith("https://i.redd.it")) {
            label.setText(" Reddit ");
            label.getStyleClass().add("reddit-badge");
        } else if (url.startsWith("https://i.imgur.com")) {
            label.setText(" Imgur ");
            label.getStyleClass().add("imgur-badge");
        }

        new Thread(() -> {
            InputStream is = ImageUtils.loadImage(submission.getUrl());
            final Image image = is != null ? new Image(is) : null;
            Platform.runLater(() -> {
                if (image != null)
                    ImageController.this.image.setImage(image);
            });
        }).start();

    }

    @Override
    public Pane getBox() {
        return box;
    }
}

package de.zortax.zreddit.controller.submission;// Created by leo on 27.02.18

import com.jfoenix.controls.JFXButton;
import de.zortax.zreddit.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import net.dean.jraw.models.Submission;

import java.io.InputStream;

public class ImageController implements SubmissionElement {

    @FXML public HBox box;
    @FXML public ImageView image;
    @FXML public Label label;
    @FXML public JFXButton labelButton;

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
            InputStream is = Utils.loadImage(submission.getUrl());
            final Image image = is != null ? new Image(is) : null;
            Platform.runLater(() -> {
                if (image != null)
                    ImageController.this.image.setImage(image);
            });
        }).start();

        this.labelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Utils.browse(url));

    }

    @Override
    public Pane getPane() {
        return box;
    }
}

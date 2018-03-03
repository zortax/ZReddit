package de.zortax.zreddit.controller.submission;// Created by leo on 03.03.18

import com.jfoenix.controls.JFXButton;
import de.zortax.zreddit.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import net.dean.jraw.models.Submission;

import java.io.InputStream;

public class GifController implements SubmissionElement {

    @FXML public HBox box;
    @FXML public ImageView image;
    @FXML public AnchorPane playbackPane;
    @FXML public JFXButton playButton;
    @FXML public ImageView playButtonImage;
    @FXML public Slider slider;
    @FXML public JFXButton labelButton;
    @FXML public Label label;

    @FXML
    public void initialize() {

    }

    @Override
    public void init(Submission submission) {
        new Thread(() -> {
            InputStream is = Utils.loadImage(submission.getUrl());
            System.out.println(is != null);
            if (is != null) {
                Platform.runLater(() -> {
                    //ImageAnimation animation = new AnimatedGif(is, image);
                    //playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> animation.playFromStart());
                });
            }
        }).run();
    }

    @Override
    public Pane getPane() {
        return box;
    }
    
}

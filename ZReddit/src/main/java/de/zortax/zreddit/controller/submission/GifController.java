package de.zortax.zreddit.controller.submission;// Created by leo on 03.03.18

import com.jfoenix.controls.JFXButton;
import de.zortax.zreddit.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

        String url = submission.getUrl().replace(".gifv", ".gif");

        if (url.startsWith("https://i.redd.it")) {
            label.setText(" Reddit ");
            label.getStyleClass().add("reddit-badge");
        } else if (url.startsWith("https://i.imgur.com")) {
            label.setText(" Imgur ");
            label.getStyleClass().add("imgur-badge");
        }

        new Thread(() -> {
            final InputStream is = Utils.loadImage(url);
            if (is != null) {
                Image i = new Image(is);
                Platform.runLater(() -> image.setImage(i));
            }
        }).start();

        this.labelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Utils.browse(url));
        System.out.println("Init done!");
    }

    @Override
    public Pane getPane() {
        return box;
    }
    
}

package de.zortax.zreddit.controller.submission;// Created by leo on 03.03.18

import com.jfoenix.controls.JFXButton;
import de.zortax.zreddit.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import net.dean.jraw.models.Submission;

public class YouTubeController implements SubmissionElement {

    @FXML public HBox box;
    @FXML public WebView webView;
    @FXML public Label label;
    @FXML public JFXButton labelButton;

    @FXML
    public void initialize() {
        label.setText(" YouTube ");
        label.getStyleClass().add("youtube-badge");
    }

    @Override
    public void init(Submission submission) {
        webView.getEngine().load("https://www.youtube.com/embed/" + getCode(submission.getUrl()));
        labelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Utils.browse(submission.getUrl()));
    }

    @Override
    public Pane getPane() {
        return box;
    }

    private String getCode(String url) {
        if (url.contains("youtu.be/"))
            return url.split("youtu.be/")[1];
        else if (url.contains("youtube.com/"))
            return url.split("v=")[1];
        else return null;
    }
}

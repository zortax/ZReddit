package de.zortax.zreddit.controller.submission;// Created by leo on 27.02.18

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import net.dean.jraw.models.Submission;

public class LinkController implements SubmissionElement {

    @FXML public JFXButton button;
    @FXML public HBox box;

    private String url;

    @FXML
    public void initialize() {

    }

    @Override
    public void init(Submission submission) {
        this.setLink(submission.getUrl());
    }

    public void setLink(String url) {
        this.url = url;
        this.button.setText(url);
    }

    @Override
    public Pane getBox() {
        return this.box;
    }

}

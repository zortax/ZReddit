package de.zortax.zreddit.controller.submission;// Created by leo on 27.02.18

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import net.dean.jraw.models.Submission;

public class CitatedTextController implements SubmissionElement {

    @FXML public AnchorPane box;
    @FXML public Text citationText;

    @FXML
    public void initialize() {

    }

    @Override
    public void init(Submission submission) {
        this.setText(submission.getSelfText());
    }

    @Override
    public Pane getPane() {
        return box;
    }

    public void setText(String text) {
        this.citationText.setText(text);
    }

}

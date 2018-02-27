package de.zortax.zreddit.controller.submission;// Created by leo on 27.02.18

import javafx.scene.layout.Pane;
import net.dean.jraw.models.Submission;

public interface SubmissionElement {

    void init(Submission submission);
    Pane getBox();

}

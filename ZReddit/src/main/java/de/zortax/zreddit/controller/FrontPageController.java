package de.zortax.zreddit.controller;// Created by leo on 25.02.18

import de.zortax.zreddit.ZReddit;
import de.zortax.zreddit.controller.submission.SubmissionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;

public class FrontPageController {

    @FXML  public ListView postList;

    @FXML
    public void initialize() {

        for (Submission submission : ZReddit.getRedditManager().getReddit().frontPage()
                .limit(30)
                .sorting(SubredditSort.TOP)
                .timePeriod(TimePeriod.DAY)
                .build().next()) {

            try {

                FXMLLoader loader = new FXMLLoader(ZReddit.class.getResource("/fxml/submission/submission.fxml"));
                AnchorPane submissionPane = loader.load();
                SubmissionController controller = loader.getController();
                controller.init(submission);

                postList.getItems().add(submissionPane);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}

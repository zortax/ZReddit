package de.zortax.zreddit.controller;// Created by leo on 25.02.18

import de.zortax.pra.network.event.EventHandler;
import de.zortax.zreddit.ZReddit;
import de.zortax.zreddit.controller.submission.SubmissionController;
import de.zortax.zreddit.controller.submission.UISubmissionType;
import de.zortax.zreddit.events.SubmissionWrappingEvent;
import de.zortax.zreddit.utils.Utils;
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

        ZReddit.getEventManager().addListener(this);

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

    @EventHandler
    public void onSubmissionWrapping(SubmissionWrappingEvent event) {
        Submission submission = event.getSubmission();
        if (submission.isSelfPost()) {
            if (submission.getSelfText().isEmpty())
                event.setSubmissionType(UISubmissionType.EMPTY);
            else
                event.setSubmissionType(UISubmissionType.CITATION);
        } else {
            if (Utils.isImageURL(submission.getUrl()))
                event.setSubmissionType(UISubmissionType.IMAGE);
            else if (Utils.isYouTubeURL(submission.getUrl()))
                event.setSubmissionType(UISubmissionType.YOUTUBE);
            else
                event.setSubmissionType(UISubmissionType.LINK);
        }
    }

}

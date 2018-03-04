package de.zortax.zreddit.controller;// Created by leo on 25.02.18

import com.jfoenix.controls.JFXSpinner;
import de.zortax.pra.network.event.EventHandler;
import de.zortax.zreddit.ZReddit;
import de.zortax.zreddit.controller.submission.SubmissionController;
import de.zortax.zreddit.controller.submission.UISubmissionType;
import de.zortax.zreddit.events.SubmissionWrappingEvent;
import de.zortax.zreddit.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubredditSort;
import net.dean.jraw.models.TimePeriod;

import java.util.List;

public class FrontPageController {

    @FXML public ListView postList;
    private HBox box;

    @FXML
    public void initialize() {

        ZReddit.getEventManager().addListener(this);

        box = new HBox();
        box.setAlignment(Pos.CENTER);
        JFXSpinner spinner = new JFXSpinner();
        spinner.setRadius(10);
        box.getChildren().add(spinner);
        Platform.runLater(this::load);
    }

    public void load() {
        new Thread(() -> {
            List<Submission> submissions = ZReddit.getRedditManager().getReddit().frontPage()
                    .limit(30)
                    .sorting(SubredditSort.TOP)
                    .timePeriod(TimePeriod.DAY)
                    .build().next();

            for (Submission submission : submissions) {

                Platform.runLater(() -> {
                    try {

                        FXMLLoader loader = new FXMLLoader(ZReddit.class.getResource("/fxml/submission/submission.fxml"));
                        AnchorPane submissionPane = loader.load();
                        SubmissionController controller = loader.getController();
                        controller.init(submission);

                        postList.getItems().add(submissionPane);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> postList.getItems().add(box));
        }).start();
    }

    @EventHandler
    public void onSubmissionWrapping(SubmissionWrappingEvent event) {
        Submission submission = event.getSubmission();
        if (submission.isSelfPost()) {
            if (submission.getSelfText().isEmpty() || submission.getSelfText().replace(" ", "").replace("\n", "").isEmpty())
                event.setSubmissionType(UISubmissionType.EMPTY);
            else
                event.setSubmissionType(UISubmissionType.CITATION);
        } else {
            if (Utils.isImageURL(submission.getUrl()))
                event.setSubmissionType(UISubmissionType.IMAGE);
            else if (Utils.isYouTubeURL(submission.getUrl()))
                event.setSubmissionType(UISubmissionType.YOUTUBE);
            else if (Utils.isGifURL(submission.getUrl()))
                event.setSubmissionType(UISubmissionType.GIF);
            else
                event.setSubmissionType(UISubmissionType.LINK);
        }
    }

}

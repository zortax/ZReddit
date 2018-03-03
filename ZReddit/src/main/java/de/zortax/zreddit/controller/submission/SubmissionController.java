package de.zortax.zreddit.controller.submission;// Created by leo on 25.02.18

import de.zortax.zreddit.ZReddit;
import de.zortax.zreddit.animations.RippleHandler;
import de.zortax.zreddit.events.SubmissionWrappingEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import net.dean.jraw.models.Submission;

public class SubmissionController {

    @FXML  public Label postTitle;
    @FXML  public Label userLink;
    @FXML  public Label subredditLink;
    @FXML  public HBox placeholderBox;
    @FXML  public StackPane upvoteStack;
    @FXML  public ImageView upvoteImage;
    @FXML  public StackPane downvoteStack;
    @FXML  public ImageView downvoteImage;
    @FXML  public StackPane commentsStack;
    @FXML  public ImageView commentsImage;
    @FXML  public Label commentCount;
    @FXML  public Label points;
    @FXML  public StackPane optionsStack;
    @FXML  public ImageView optionsImage;

    private Submission submission;
    private UISubmissionType type;

    @FXML
    public void initialize() {

    }

    public void init(Submission submission) {

        this.postTitle.setText(submission.getTitle());
        this.userLink.setText("/u/" + submission.getAuthor());
        this.subredditLink.setText("/r/" + submission.getSubreddit());
        this.points.setText(String.valueOf(submission.getScore()));
        this.commentCount.setText(String.valueOf(submission.getCommentCount()));

        new RippleHandler(upvoteStack, 0.25, 0.4);
        new RippleHandler(downvoteStack, 0.25, 0.4);
        new RippleHandler(commentsStack, 0.25, 0.4);
        new RippleHandler(optionsStack, 0.25, 0.4);

        SubmissionWrappingEvent event = new SubmissionWrappingEvent(submission);
        ZReddit.getEventManager().callEvent(event);
        UISubmissionType type = event.getSubmissionType();

        if (!type.equals(UISubmissionType.EMPTY)) {
            SubmissionElement element = type.createInstance();
            element.init(submission);
            placeholderBox.getChildren().add(element.getPane());
        }

    }

}

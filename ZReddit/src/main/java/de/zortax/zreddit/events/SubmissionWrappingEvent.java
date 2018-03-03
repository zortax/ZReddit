package de.zortax.zreddit.events;// Created by leo on 03.03.18

import de.zortax.pra.network.event.Event;
import de.zortax.zreddit.controller.submission.UISubmissionType;
import net.dean.jraw.models.Submission;

public class SubmissionWrappingEvent implements Event {

    private Submission submission;
    private UISubmissionType submissionType;

    public SubmissionWrappingEvent(Submission submission) {
        this.submission = submission;
        this.submissionType = UISubmissionType.EMPTY;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmissionType(UISubmissionType submissionType) {
        this.submissionType = submissionType;
    }

    public UISubmissionType getSubmissionType() {
        return this.submissionType;
    }

}

package de.zortax.zreddit.animations;// Created by leo on 25.02.18

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class RippleHandler implements EventHandler<MouseEvent> {

    private Circle circleRipple;
    private Rectangle rippleClip = new Rectangle();
    private Duration rippleDuration =  Duration.millis(250);
    private double lastRippleHeight = 0;
    private double lastRippleWidth = 0;
    private Color rippleColor = new Color(0, 0, 0, 0.11);
    private final SequentialTransition parallelTransition;
    private final Timeline scaleRippleTimeline;
    private Pane box;

    RippleHandler(Pane box) {
        this.box = box;
        circleRipple = new Circle(0.1, rippleColor);
        circleRipple.setOpacity(0.0);

        final FadeTransition fadeTransition = new FadeTransition(rippleDuration, circleRipple);
        fadeTransition.setInterpolator(Interpolator.EASE_OUT);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        scaleRippleTimeline = new Timeline();

        parallelTransition = new SequentialTransition();
        parallelTransition.getChildren().addAll(
                scaleRippleTimeline,
                fadeTransition
        );

        parallelTransition.setOnFinished(event1 -> {
            circleRipple.setOpacity(0.0);
            circleRipple.setRadius(0.1);
        });

        box.addEventHandler(MouseEvent.MOUSE_PRESSED, this);
        box.getChildren().add(1, circleRipple);
    }


    public void handle(MouseEvent event) {
        parallelTransition.stop();
        parallelTransition.getOnFinished().handle(null);

        circleRipple.setCenterX(event.getX());
        circleRipple.setCenterY(event.getY());

        if (box.getWidth() != lastRippleWidth || box.getHeight() != lastRippleHeight) {
            lastRippleWidth = box.getWidth();
            lastRippleHeight = box.getHeight();

            rippleClip.setWidth(lastRippleWidth);
            rippleClip.setHeight(lastRippleHeight);

            try {
                rippleClip.setArcHeight(box.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius());
                rippleClip.setArcWidth(box.getBackground().getFills().get(0).getRadii().getTopLeftHorizontalRadius());
                circleRipple.setClip(rippleClip);
            } catch (Exception ignored) {}

            double circleRippleRadius = Math.max(box.getHeight(), box.getWidth()) * 0.35;
            final KeyValue keyValue = new KeyValue(circleRipple.radiusProperty(), circleRippleRadius, Interpolator.EASE_OUT);
            final KeyFrame keyFrame = new KeyFrame(rippleDuration, keyValue);
            scaleRippleTimeline.getKeyFrames().clear();
            scaleRippleTimeline.getKeyFrames().add(keyFrame);
        }

        parallelTransition.playFromStart();
    }
}

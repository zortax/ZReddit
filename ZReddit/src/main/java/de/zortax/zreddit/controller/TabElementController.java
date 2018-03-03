package de.zortax.zreddit.controller;// Created by leo on 26.02.18

import de.zortax.zreddit.ZReddit;
import de.zortax.zreddit.animations.RippleHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class TabElementController {

    @FXML public ImageView image;
    @FXML public StackPane stackPane;

    private RippleHandler rippleHandler;

    @FXML
    public void initialize() {
        this.rippleHandler = new RippleHandler(stackPane);
    }

    public void setImage(String url) {
        image.setImage(new Image(ZReddit.class.getResourceAsStream(url)));
    }

    public RippleHandler getRippleHandler() {
        return rippleHandler;
    }

}

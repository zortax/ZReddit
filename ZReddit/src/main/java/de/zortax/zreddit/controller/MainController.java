package de.zortax.zreddit.controller;// Created by leo on 25.02.18

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML public HBox homeButton;
    @FXML public HBox gotoButton;
    @FXML public HBox searchButton;
    @FXML public HBox messagesButton;
    @FXML public HBox settingsButton;
    @FXML public ImageView homeImage;
    @FXML public ImageView gotoImage;
    @FXML public ImageView searchImage;
    @FXML public ImageView messagesImage;
    @FXML public ImageView settingsImage;
    @FXML public StackPane homeStack;
    @FXML public StackPane gotoStack;
    @FXML public StackPane searchStack;
    @FXML public StackPane messagesStack;
    @FXML public StackPane settingsStack;
    @FXML public AnchorPane pagePane;

    private AnchorPane frontPage;
    private AnchorPane searchPage;
    private AnchorPane messagesPane;
    private AnchorPane settingsPane;

    @FXML
    public void initialize() {

    }



}

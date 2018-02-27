package de.zortax.zreddit.controller;// Created by leo on 25.02.18

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import de.zortax.zreddit.Main;
import de.zortax.zreddit.events.UIReloadEvent;
import de.zortax.zreddit.reddit.RedditState;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SigninController {

    @FXML public AnchorPane anchorPane;
    @FXML public Label title;
    @FXML public Label prompt;
    @FXML public JFXButton reloadButton;
    @FXML public WebView webView;

    private WebEngine webEngine;
    private HBox box;

    @FXML
    public void initialize() {
        title.setText(Main.getMessage("zreddit.ui.signin.title"));
        prompt.setText(Main.getMessage("zreddit.ui.signin.need_authentication"));
        reloadButton.setText(Main.getMessage("zreddit.ui.signin.reload"));

        webEngine = webView.getEngine();
        reloadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> webEngine.load(Main.getRedditManager().authenticateUser()));
        webEngine.load(Main.getRedditManager().authenticateUser());
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            String url = webEngine.getLocation();
            if (Main.getRedditManager().isFinalUrl(url) && url != null) {
                anchorPane.getChildren().remove(webView);
                box = new HBox();
                box.setAlignment(Pos.CENTER);
                AnchorPane.setTopAnchor(box, 90D);
                AnchorPane.setBottomAnchor(box, 15D);
                AnchorPane.setRightAnchor(box, 15D);
                AnchorPane.setLeftAnchor(box, 15D);
                box.getChildren().add(new JFXSpinner());
                anchorPane.getChildren().add(box);
                try {
                    Main.getRedditManager().completeAuth(url);
                } catch (Exception e) {
                    e.printStackTrace();
                    Main.getRedditManager().completeAuth(url);
                }
                if (Main.getRedditManager().getState() == RedditState.CONNECTED)
                    Main.getEventManager().callEvent(new UIReloadEvent());
            }
        });
    }

    public void reset() {
        anchorPane.getChildren().remove(box);
        anchorPane.getChildren().add(webView);
        webEngine.load(Main.getRedditManager().authenticateUser());
    }

}

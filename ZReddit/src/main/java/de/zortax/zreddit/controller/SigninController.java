package de.zortax.zreddit.controller;// Created by leo on 25.02.18

import com.jfoenix.controls.JFXButton;
import de.zortax.zreddit.Main;
import de.zortax.zreddit.events.UIReloadEvent;
import de.zortax.zreddit.reddit.RedditState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class SigninController {

    @FXML public Label title;
    @FXML public Label prompt;
    @FXML public JFXButton reloadButton;
    @FXML public WebView webView;

    private WebEngine webEngine;

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
                Main.getRedditManager().completeAuth(url);
                if (Main.getRedditManager().getState() == RedditState.CONNECTED)
                    Main.getEventManager().callEvent(new UIReloadEvent());
            }
        });
    }

}

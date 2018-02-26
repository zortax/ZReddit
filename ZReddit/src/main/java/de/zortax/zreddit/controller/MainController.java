package de.zortax.zreddit.controller;// Created by leo on 25.02.18

import de.zortax.pra.network.event.EventHandler;
import de.zortax.zreddit.Main;
import de.zortax.zreddit.events.TabSelectedEvent;
import de.zortax.zreddit.reddit.RedditState;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML public AnchorPane pagePane;
    @FXML public VBox topTabBox;
    @FXML public VBox bottomTabBox;
    @FXML public HBox image;


    @FXML
    public void initialize() {
        Main.getEventManager().addListener(this);
        reload();
    }

    public void reload() {
        topTabBox.getChildren().clear();
        bottomTabBox.getChildren().clear();
        topTabBox.getChildren().add(image);
        boolean loggedIn = Main.getRedditManager().getState() == RedditState.CONNECTED;
        for (ApplicationPage p : ApplicationPage.values())
            addTab(p, loggedIn);
        topTabBox.getChildren().add(bottomTabBox);
        ApplicationPage.values()[0].select();
    }

    public void addTab(ApplicationPage page, boolean loggedIn) {
        if (loggedIn) {
            if (!page.onlyLogin()) {
                if (page.addAtBottom())
                    bottomTabBox.getChildren().add(page.getTabElement());
                else
                    topTabBox.getChildren().add(page.getTabElement());
            }
        } else {
            if (!page.needsLogin()) {
                if (page.addAtBottom())
                    bottomTabBox.getChildren().add(page.getTabElement());
                else
                    topTabBox.getChildren().add(page.getTabElement());
            }
        }
    }

    @EventHandler
    public void onTabSelected(TabSelectedEvent event) {
        if (!event.isCancelled()) {
            pagePane.getChildren().clear();
            pagePane.getChildren().add(event.getPagePane());
        }
    }



}

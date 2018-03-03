package de.zortax.zreddit.controller;// Created by leo on 26.02.18

import de.zortax.zreddit.ZReddit;
import de.zortax.zreddit.events.TabClickedEvent;
import de.zortax.zreddit.events.TabSelectedEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.net.URL;

public enum ApplicationPage {

    SIGNIN("signin_page.fxml", "signin.png", false, true, false),
    FRONT_PAGE("front_page.fxml", "home.png", false),
    SETTINGS("settings_page.fxml", "settings.png", false, false, true);

    public static ApplicationPage currentlySelected = null;

    private String fxml;
    private String icon;
    private boolean needLogin;
    private boolean addAtBottom;
    private TabElementController elementController;
    private HBox tabElement;
    private boolean isSelected;
    private AnchorPane page;
    private boolean onlyLogin;

    ApplicationPage(String fxml, String icon, boolean needLogin) {
        this(fxml, icon, needLogin, false, false);
    }

    ApplicationPage(String fxml, String icon, boolean needLogin, boolean onlyLogin, boolean addAtBottom) {
        this.fxml = fxml;
        this.icon = icon;
        this.needLogin = needLogin;
        this.addAtBottom = addAtBottom;
        this.isSelected = false;
        this.onlyLogin = onlyLogin;

        try {
            FXMLLoader elementLoader = new FXMLLoader(ZReddit.class.getResource("/fxml/tab_element.fxml"));
            this.tabElement = elementLoader.load();
            this.elementController = elementLoader.getController();
            this.elementController.setImage("/icons/" + this.icon);

            if (!(fxml.isEmpty() || fxml.equals("none"))) {
                page = FXMLLoader.load(getFxmlURL());
                AnchorPane.setRightAnchor(page, 0D);
                AnchorPane.setLeftAnchor(page, 0D);
                AnchorPane.setBottomAnchor(page, 0D);
                AnchorPane.setTopAnchor(page, 0D);
            }

            this.tabElement.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                TabClickedEvent event = new TabClickedEvent(ApplicationPage.this);
                ZReddit.getEventManager().callEvent(event);
                if (!event.isCancelled())
                    select();
            });

        } catch (Exception e) {
            ZReddit.getLogger().warning(e.getMessage());
            e.printStackTrace();
        }

    }

    public void unselect() {
        this.isSelected = false;
        this.tabElement.getStyleClass().removeIf(s -> s.equals("selected-buttons"));
    }

    public void select() {
        if (isSelected())
            return;
        TabSelectedEvent event = new TabSelectedEvent(this);
        ZReddit.getEventManager().callEvent(event);
        if (!event.isCancelled()) {
            this.isSelected = true;
            this.tabElement.getStyleClass().add("selected-buttons");
            if (ApplicationPage.currentlySelected != null)
                ApplicationPage.currentlySelected.unselect();
            ApplicationPage.currentlySelected = this;
        }
    }

    public TabElementController getElementController() {
        return elementController;
    }

    public String getFxml() {
        return fxml;
    }

    public URL getFxmlURL() {
        return ZReddit.class.getResource("/fxml/" + fxml);
    }

    public URL getImageURL() {
        return ZReddit.class.getResource("/icons/" + icon);
    }

    public boolean needsLogin() {
        return needLogin;
    }

    public boolean addAtBottom() {
        return addAtBottom;
    }

    public HBox getTabElement() {
        return tabElement;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean hasPage() {
        return page != null;
    }

    public AnchorPane getPage() {
        return page;
    }

    public boolean onlyLogin() {
        return onlyLogin;
    }

}

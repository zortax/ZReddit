package de.zortax.zreddit.events;// Created by leo on 26.02.18

import de.zortax.pra.network.event.Cancellable;
import de.zortax.pra.network.event.Event;
import de.zortax.zreddit.controller.ApplicationPage;

public class TabClickedEvent implements Event, Cancellable{

    private boolean cancelled;
    private ApplicationPage page;

    public TabClickedEvent(ApplicationPage page) {
        this.page = page;
        this.cancelled = false;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    public ApplicationPage getPage() {
        return page;
    }
}

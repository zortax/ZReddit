package de.zortax.zreddit.events;// Created by leo on 27.02.18

import de.zortax.pra.network.event.Event;
import de.zortax.zreddit.reddit.RedditState;

public class RedditStateChangedEvent implements Event {

    private RedditState oldState;
    private RedditState newState;

    public RedditStateChangedEvent(RedditState oldState, RedditState newState) {
        this.oldState = oldState;
        this.newState = newState;
    }

    public RedditState getOldState() {
        return oldState;
    }

    public RedditState getNewState() {
        return newState;
    }
}

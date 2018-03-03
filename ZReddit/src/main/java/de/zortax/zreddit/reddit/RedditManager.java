package de.zortax.zreddit.reddit;// Created by leo on 26.02.18

import de.zortax.zreddit.ZReddit;
import de.zortax.zreddit.events.RedditStateChangedEvent;
import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.AccountHelper;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.StatefulAuthHelper;

import java.util.UUID;

public class RedditManager {

    private RedditState state;

    private UserAgent userAgent;
    private NetworkAdapter networkAdapter;
    private Credentials credentials;
    private RedditClient reddit;
    private AccountHelper accountHelper;
    private StatefulAuthHelper statefulAuthHelper;

    private String[] scopes = {
            "identity",
            "edit",
            "flair",
            "history",
            "mysubreddits",
            "privatemessages",
            "read",
            "report",
            "save",
            "submit",
            "subscribe",
            "vote",
            "wikiread"
    };

    public RedditManager() {
        state = RedditState.NOT_CONNECTED;
    }

    public void connect() {
        new Thread(() -> {
            userAgent = new UserAgent("ZReddit", "de.zortax.zreddit", ZReddit.ZREDDIT_VERSION, "Zortax_");
            networkAdapter = new OkHttpNetworkAdapter(userAgent);
            credentials = Credentials.installedApp("m8e7-WxZkPBTiQ", "https://zreddit.zortax.de");
            accountHelper = new AccountHelper(networkAdapter, credentials, ZReddit.getConfig(), UUID.fromString(ZReddit.getConfig().deviceID));
            if (ZReddit.getConfig().accounts.isEmpty() || !ZReddit.getConfig().accounts.containsKey(ZReddit.getConfig().lastAccount)) {
                reddit = accountHelper.switchToUserless();
                RedditStateChangedEvent event = new RedditStateChangedEvent(RedditState.NOT_CONNECTED, RedditState.CONNECTED_NO_AUTH);
                state = RedditState.CONNECTED_NO_AUTH;
                ZReddit.getEventManager().callEvent(event);
            } else {
                ZReddit.getLogger().info("Trying to log into \"" + ZReddit.getConfig().lastAccount + "\"!");
                reddit = accountHelper.trySwitchToUser(ZReddit.getConfig().lastAccount);
                if (reddit == null) {
                    ZReddit.getLogger().info("Failed! Switching to userless mode...");
                    reddit = accountHelper.switchToUserless();
                    RedditStateChangedEvent event = new RedditStateChangedEvent(RedditState.NOT_CONNECTED, RedditState.CONNECTED_NO_AUTH);
                    state = RedditState.CONNECTED_NO_AUTH;
                    ZReddit.getEventManager().callEvent(event);
                } else {
                    ZReddit.getLogger().info("Success! Calling event listeners...");
                    RedditStateChangedEvent event = new RedditStateChangedEvent(RedditState.NOT_CONNECTED, RedditState.CONNECTED);
                    state = RedditState.CONNECTED;
                    ZReddit.getEventManager().callEvent(event);
                }
            }
        }).start();
    }

    public String authenticateUser() {
        statefulAuthHelper = accountHelper.switchToNewUser();
        String url = statefulAuthHelper.getAuthorizationUrl(true, true, scopes);
        state = RedditState.WAITING_FOR_AUTH;
        return url;
    }

    public boolean isFinalUrl(String url) {
        return statefulAuthHelper.isFinalRedirectUrl(url);
    }

    public void completeAuth(String finalUrl) {
        reddit = statefulAuthHelper.onUserChallenge(finalUrl);
        RedditStateChangedEvent event = new RedditStateChangedEvent(state, RedditState.CONNECTED);
        state = RedditState.CONNECTED;
        ZReddit.getConfig().lastAccount = reddit.me().getUsername();
        ZReddit.getEventManager().callEvent(event);
        ZReddit.getConfig().save();
    }

    public RedditClient getReddit() {
        return reddit;
    }

    public RedditState getState() {
        return state;
    }

}

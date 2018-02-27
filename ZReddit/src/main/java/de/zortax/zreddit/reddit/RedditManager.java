package de.zortax.zreddit.reddit;// Created by leo on 26.02.18

import de.zortax.zreddit.Main;
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
        userAgent = new UserAgent("ZReddit", "de.zortax.zreddit", Main.ZREDDIT_VERSION, "Zortax_");
        networkAdapter = new OkHttpNetworkAdapter(userAgent);
        credentials = Credentials.installedApp("m8e7-WxZkPBTiQ", "https://zreddit.zortax.de");
        accountHelper = new AccountHelper(networkAdapter, credentials, Main.getConfig(), UUID.fromString(Main.getConfig().deviceID));
        if (Main.getConfig().accounts.isEmpty() || !Main.getConfig().accounts.containsKey(Main.getConfig().lastAccount))
            reddit = accountHelper.switchToUserless();
        else {
            reddit = accountHelper.trySwitchToUser(Main.getConfig().lastAccount);
            if (reddit == null) {
                reddit = accountHelper.switchToUserless();
                System.out.println("PENIS");
            }
            else
                state = RedditState.CONNECTED;
        }
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
        Main.getConfig().lastAccount = reddit.me().getUsername();
        Main.getEventManager().callEvent(event);
        Main.getConfig().save();
    }

    public RedditClient getReddit() {
        return reddit;
    }

    public RedditState getState() {
        return state;
    }

}

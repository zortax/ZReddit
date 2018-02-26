package de.zortax.zreddit.config;// Created by leo on 26.02.18

import de.zortax.pra.network.config.Config;
import net.dean.jraw.models.OAuthData;
import net.dean.jraw.oauth.TokenStore;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// TODO: store tokens securely

public class ClientConfig extends Config implements TokenStore {

    public String language = "default";
    public String lastAccount = "none";
    public HashMap<String, RedditAccount> accounts = new HashMap<>();
    public HashMap<String, String> refreshTokens = new HashMap<>();
    public String deviceID = UUID.randomUUID().toString();

    @Override
    public void storeLatest(String username, OAuthData oAuthData) {
        accounts.put(username, new RedditAccount(username, oAuthData));
    }

    @Override
    public void storeRefreshToken(String username, String refreshToken) {
        refreshTokens.put(username, refreshToken);
    }

    @Nullable
    @Override
    public OAuthData fetchLatest(String username) {
        RedditAccount acc = accounts.getOrDefault(username, null);
        return acc == null ? null : OAuthData.create(
                acc.getAccessToken(),
                acc.getScopes(),
                refreshTokens.getOrDefault(username, null),
                acc.getExpiration()
        );
    }

    @Nullable
    @Override
    public String fetchRefreshToken(String username) {
        return refreshTokens.getOrDefault(username, null);
    }

    @Override
    public void deleteLatest(String username) {
        if (accounts.containsKey(username))
            accounts.remove(username);
    }

    @Override
    public void deleteRefreshToken(String username) {
        if (refreshTokens.containsKey(username))
            refreshTokens.remove(username);
    }

    public static class RedditAccount {

        private String userName;
        private String accessToken;
        private Date expiration;
        private List<String> scopes;

        public RedditAccount(String username, OAuthData data) {
            this.userName = username;
            this.accessToken = data.getAccessToken();
            this.expiration = data.getExpiration();
            this.scopes = data.getScopes();
        }


        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public Date getExpiration() {
            return expiration;
        }

        public void setExpiration(Date expiration) {
            this.expiration = expiration;
        }

        public List<String> getScopes() {
            return scopes;
        }

        public void setScopes(List<String> scopes) {
            this.scopes = scopes;
        }
    }
}

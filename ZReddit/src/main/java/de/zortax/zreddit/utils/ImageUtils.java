package de.zortax.zreddit.utils;// Created by leo on 27.02.18

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageUtils {

    public static boolean isImageURL(String url) {
        return url.toLowerCase().endsWith(".jpg");
    }

    public static InputStream loadImage(String link) {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            return connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

package de.zortax.zreddit.utils;// Created by leo on 27.02.18

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Utils {

    public static boolean isImageURL(String url) {
        return url.toLowerCase().endsWith(".jpg");
    }

    public static boolean isGifURL(String url) {
        return url.toLowerCase().endsWith(".gif");
    }

    public static boolean isYouTubeURL(String url) {
        return url.contains("youtube.com") || url.contains("youtu.be");
    }

    public static InputStream loadImage(String link) {
        try {
            URL url = new URL(link);
            URLConnection connection = url.openConnection();
            return connection.getInputStream();
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }

    public static void browse(String url) {
        OSType os = getOSType();
        if (os == OSType.WINDOWS) {
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (os == OSType.MAC) {
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (os == OSType.LINUX) {
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("xdg-open " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static OSType getOSType() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return OSType.WINDOWS;
        else if (os.contains("mac")) return OSType.MAC;
        else if (os.contains("nix") || os.contains("nux")) return OSType.LINUX;
        else return OSType.UNKNOWN;
    }

    public static enum OSType {
        WINDOWS, LINUX, MAC, UNKNOWN;
    }

}

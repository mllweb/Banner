package com.mllweb.banner;

/**
 * Created by AndyJeason on 2016/9/29.
 */

public class BannerPage {
    private String title;
    private String uriString;
    private int uriRes;
    private String link;


    public BannerPage(String uriString) {
        this.uriString = uriString;
    }

    public BannerPage(int uriRes) {
        this.uriRes = uriRes;
    }

    public BannerPage(String uriString, String link) {
        this.uriString = uriString;
        this.link = link;
    }

    public BannerPage(int uriRes, String link) {

        this.uriRes = uriRes;
        this.link = link;
    }

    public BannerPage(String title, int uriRes, String link) {
        this.title = title;
        this.uriRes = uriRes;
        this.link = link;
    }

    public BannerPage(String link, String title, String uriString) {
        this.link = link;
        this.title = title;
        this.uriString = uriString;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUriString() {
        return uriString;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    public int getUriRes() {
        return uriRes;
    }

    public void setUriRes(int uriRes) {
        this.uriRes = uriRes;
    }
}

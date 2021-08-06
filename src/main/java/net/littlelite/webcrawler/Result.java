/**
 * WebCrawler
 * Copyright (C) 2021 Alessio Saltarin
 * MIT License
 */

package net.littlelite.webcrawler;

public class Result {

    private int depth;
    private String webSite;
    private String url;
    private String result;

    public Result(String website, int depth, String url) {
        this.webSite = website;
        this.depth = depth;
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}

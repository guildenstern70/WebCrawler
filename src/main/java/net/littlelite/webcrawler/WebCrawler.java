/**
 * WebCrawler
 * Copyright (C) 2021 Alessio Saltarin
 * MIT License
 */

package net.littlelite.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class WebCrawler {

    private static final int MAX_DEPTH = 5;

    private final HashSet<String> links;
    private final List<Result> resultList;
    private String webSite;

    public WebCrawler() {
        this.links = new HashSet<>();
        this.resultList = new ArrayList<>();
    }

    public void getPageLinks() {
        this.getPageLinks("https://" +  this.webSite, 0);
    }

    private void getPageLinks(String URL, int depth) {
        if ((!links.contains(URL) && (depth < MAX_DEPTH))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            var result = new Result(this.webSite, depth, URL);
            try {
                links.add(URL);
                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");
                result.setResult("OK");
                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth);
                }
            } catch (IOException e) {
                result.setResult(e.getMessage());
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
            catch (IllegalArgumentException ilex) {
                System.err.println("Wrong URL: " + URL);
                ilex.printStackTrace();
                System.err.println("Retrying with www." + URL);
                this.getPageLinks("https://www." + this.webSite, 0);
            }
            this.resultList.add(result);
        }
    }

    public List<Result> getResults() {
        return resultList;
    }

    public static void main(String[] args) {
        System.out.println("WebCrawler v.0.2");
        var sites = List.of(
                "anchoreyes.com",
                "aperolspritzsocials.com"
        );
        var wc = new WebCrawler();
        sites.forEach( (site) -> {
                wc.setWebSite(site);
                wc.getPageLinks();
        });
        ResultWriter.writeToCSV(wc.getResults());
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
}

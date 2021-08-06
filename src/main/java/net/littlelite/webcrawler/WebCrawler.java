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

    private static final int MAX_DEPTH = 4;

    private HashSet<String> links;
    private List<Result> resultList;
    private String webSite;

    public WebCrawler() {
        this.links = new HashSet<>();
        this.resultList = new ArrayList<>();
    }

    public void getPageLinks() {
        this.getPageLinks(this.webSite, 0);
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
            this.resultList.add(result);
        }
    }

    public List<Result> getResults() {
        return resultList;
    }

    public static void main(String[] args) {
        System.out.println("WebCrawler v.0.2");
        var sites = List.of(
               "https://anchoreyes.com", "https://bulldoggin.com");

//        var sites = List.of(
//                "https://anchoreyes.com",
//                "https://aperolspritzsocials.com",
//                "https://appletonestate.com",
//                "https://bisquitdubouche.com",
//                "https://bulldoggin.com",
//                "https://campariacademy.com.au",
//                "https://campariacademy.it",
//                "https://crodino.com",
//                "https://espolontequila.com",
//                "https://grappafrattina.it",
//                "https://montelobos.com",
//                "https://ondina.com",
//                "https://skyyvodka.com",
//                "https://wildturkeybourbon.com",
//                "https://wildturkeybourbon.com.au",
//                "https://aperolspritz.co.nz",
//                "https://glengrant.com.au",
//                "https://amarobraulio.it",
//                "https://aperolspritz.com.au",
//                "https://grandmarnier.com",
//                "https://kokokanu.com"
//        );

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

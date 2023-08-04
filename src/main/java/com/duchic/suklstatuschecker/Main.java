package com.duchic.suklstatuschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    Document doc;

    private static final String url = "https://www.epreskripce.cz/stav-systemu-erecept";
    private String status = "unknown";
    private String bezOmezeni = null;
    private String sOmezenim = null;
    private String nedostupny = null;
    private static String appToken;
    private static String userToken;


    public Main(){

    }

    public static void main(String[] args) {
        appToken = args[0];
        userToken = args[1];
        Main run = new Main();
        run.fetchTargetWebsite(url);
    }

    public void fetchTargetWebsite(String url) {
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                    .header("Accept-Language", "*")
                    .get();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Elements products = doc.select("ul.semafor");

        bezOmezeni = products.get(0).getAllElements().get(1).attributes().get("class");
        sOmezenim = products.get(0).getAllElements().get(4).attributes().get("class");
        nedostupny = products.get(0).getAllElements().get(7).attributes().get("class");
        getStatus(bezOmezeni,sOmezenim,nedostupny);
    }

    public void getStatus(String bezOmezeni, String sOmezenim, String nedostupny) {
        if (bezOmezeni.contains("neaktivni")){
            if (sOmezenim.contains("neaktivni")){
                status = "nedostupny";
            } else {
                status = "s omezenim";
            }
        } else {
            status = "bez omezeni";
        }
        System.out.println("status: " + status);
    }

    public void sendPushoverNotification(String status) {
        try {
            Pushover pushover = new Pushover(appToken, userToken);
            pushover.sendMessage(status,"Stav eReceptu");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

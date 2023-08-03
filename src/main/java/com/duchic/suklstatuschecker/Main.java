package com.duchic.suklstatuschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    Document doc;

    public static final String url = "https://www.epreskripce.cz/stav-systemu-erecept";

    public Main(){

    }

    public static void main(String[] args) {
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
        System.out.println(products.toString());
        getElementsToList(products);
    }

    public void getElementsToList(Elements products) { //zatim nefunkcni
        List<Element> list = new ArrayList<>();

        for (int i=0; i<products.size(); i++) {
            list.set(i,products.get(i));
            System.out.println(list.get(i).toString());
        }
    }
}

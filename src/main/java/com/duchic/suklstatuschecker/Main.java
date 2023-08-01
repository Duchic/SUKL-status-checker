package com.duchic.suklstatuschecker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {

    Document doc;

    public static void main(String[] args) {

    }

    public void fetchTargetWebsite(String url) {
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

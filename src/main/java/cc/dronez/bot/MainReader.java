package cc.dronez.bot;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainReader {

    public static void main(String[] args){
        if(args.length == 2){ // Sanity check
            String websiteAddress = args[0];
            String driveLocation = args[1];

            try {
                getWebsite(websiteAddress, driveLocation);
                readWebsite(websiteAddress, driveLocation);
            } catch (Exception e){
                e.printStackTrace();
            }
        }else{
            System.out.println("Error message: arg1: website, arg2: drive locaiton");
        }
    }

    private static void readWebsite(String websiteAddress, String driveLocation) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(driveLocation));
        URL url = null;
        String line;

        url = new URL(websiteAddress);
        URLConnection conn = null;
        conn = url.openConnection();

        conn.setRequestProperty("User-Agent", "Mickes Bot");
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = br.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }
        br.close();
        writer.close();
    }


    private static void getWebsite(String websiteAddress, String driveLocation) throws IOException, MalformedURLException, SSLHandshakeException {
        URL url = new URL(websiteAddress);

        Document doc = Jsoup.parse(url, 3*1000);
        String text = doc.body().text();

        System.out.println(doc.toString());
        System.out.println(text);
    }
}

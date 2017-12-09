package no.pederyo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CsvReaderUtil {
    public static ReaderHjelp readCSVInternett(String urlen) throws IOException {
        if (urlen.contains(".html")) {
            urlen = urlen.replace("html", "csv");
        }
        URL url = new URL(urlen);
        ReaderHjelp reader = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            if (connection.getResponseCode() == 200) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(streamReader);
                String line = br.readLine() + br.readLine() + br.readLine() + br.readLine();
                String[] fieldsene;
                reader = new ReaderHjelp();
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    fieldsene = line.split(",");
                    reader.setOppData(fieldsene);
                }
                br.close();
            } else {
                System.out.println("KOMMER IKKE INN PÅ LENKEN!");
            }
        } catch (MalformedURLException e) {
            System.out.println(e);
        }
        return reader;
    }
}

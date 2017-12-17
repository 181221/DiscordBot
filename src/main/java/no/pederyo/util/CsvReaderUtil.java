package no.pederyo.util;

import no.pederyo.logg.Logg;
import no.pederyo.model.Hendelse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvReaderUtil {
    public static HashMap<String, ArrayList<Hendelse>> alleRom;

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
                String melding = "Connection failed.. " + url;
                Logg.skrivTilLogg(melding);
            }
        } catch (MalformedURLException e) {
            Logg.skrivTilLogg(e.getMessage());
        }
        return reader;
    }

    public static void readRomCSV() throws FileNotFoundException, IOException {
        alleRom = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("alleseminarogaud.csv"));
        String line = br.readLine() + br.readLine() + br.readLine() + br.readLine();
        while ((line = br.readLine()) != null) {
            String felt = line.split(",")[5];
            String romnavn = RomUtil.parseRomNavn(felt.substring(1));
            alleRom.put(romnavn, null);
        }
    }

    public static HashMap<String, ArrayList<Hendelse>> getAlleRom() {
        return alleRom;
    }
}

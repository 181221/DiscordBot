package no.pederyo.bot;

import no.pederyo.logg.Logg;
import no.pederyo.model.Hendelse;
import no.pederyo.util.DatoOgTimeUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvReader {
    public static HashMap<String, ArrayList<Hendelse>> alleRom;

    public static void readRomCSV() throws FileNotFoundException, IOException {
        alleRom = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("alleseminarogaud.csv"));
        String line = br.readLine() + br.readLine() + br.readLine() + br.readLine();
        while ((line = br.readLine()) != null) {
            String felt = line.split(",")[5];
            String romnavn = DatoOgTimeUtil.parseRomNavn(felt.substring(1));
            alleRom.put(romnavn, null);
        }
    }

    public static void readCSVInternett(String urlen) throws IOException {
        if (urlen.contains(".html")) {
            urlen = urlen.replace("html", "csv");
        }
        URL url = new URL(urlen);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            if (connection.getResponseCode() == 200) {
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(streamReader);
                String line = br.readLine() + br.readLine() + br.readLine() + br.readLine();
                String[] fieldsene;
                while ((line = br.readLine()) != null && !line.isEmpty()) {
                    fieldsene = line.split(",");
                    setOppData(fieldsene);
                }
                br.close();
            } else {
                String melding = "Connection failed.. " + url;
                Logg.skrivTilLogg(melding);
            }
        } catch (MalformedURLException e) {
            Logg.skrivTilLogg(e.getMessage());
        }
    }

    /**
     * Setter opp data fra csv fil.
     * Lager en liste med alle rommene fra csv filen.
     *
     * @param felt Datafelt fra csv fil.
     */
    private static void setOppData(String[] felt) {
        String dato = felt[0];
        String start = felt[1].substring(1);
        String slutt = felt[3].substring(1);
        String romnavn = DatoOgTimeUtil.parseRomNavn(felt[5].substring(1));
        Hendelse h = CsvReaderHjelp.lagHendelse(dato, start, slutt);
        h.setRom(CsvReaderHjelp.lagRom(romnavn));
        CsvReaderHjelp.leggTil(romnavn, h, CsvReader.alleRom);
    }
}

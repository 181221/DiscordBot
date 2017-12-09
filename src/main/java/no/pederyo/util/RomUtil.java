package no.pederyo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RomUtil {

    public static String parseData(String data) {
        String dataString = data;
        switch (data) {
            case "romnavn":
                dataString = parseRomNavn(data);
                break;
        }
        return dataString;

    }

    public static String parseRomNavn(String rom) {
        if (rom.contains("\"")) {
            rom = rom.replace('"', ' ').substring(1);
        }
        if (rom.charAt(0) == ' ') {
            rom = rom.substring(1);
        }
        return rom;
    }
    /**
     * Returnerer tidspunktet naa.
     * @return
     */
    public static int hentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date();
        return Integer.parseInt(dateFormat.format(date));
    }


}

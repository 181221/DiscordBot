package no.pederyo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatoOgTimeUtil {

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

    public static int parseTime(String time) {
        String ferdig = time.substring(0, 2);
        return Integer.parseInt(ferdig);
    }
    /**
     * Returnerer tidspunktet naa.
     * @return
     */
    public static int hentTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String datoen = dateFormat.format(date);
        String ferdig = datoen.substring(0, 2) + datoen.substring(3, 5);
        return Integer.parseInt(ferdig);
    }


}

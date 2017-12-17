package no.pederyo.klient;

import no.pederyo.util.CsvReaderUtil;
import no.pederyo.util.ReaderHjelp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static no.pederyo.util.CsvReaderUtil.*;

/**
 * Created by Peder on 17.12.2017.
 */
public class Test {
    public static final String TESTLINK = "https://no.timeedit.net/web/hib/db1/service/ri1AY6YYcnd8v5QYwYQrxgb1ZxgYxm98KaYravr5jY5awSadjc8vm5ZQ0Q022x60Yy0505YgX6g5Z5353Yg.html";
    public static final String SEMINARROM = "https://no.timeedit.net/web/hib/db1/service/ri1AY6YYcnd8v5QYwYQrxgb1ZxgYxm98KaYravr5jY5awSadjc8vm5ZQ2Q562x50Yy5603W606g5Z53.html";
    public static ReaderHjelp reader;

    public static void main(String[] args) throws IOException {
        readRomCSV();
        readCSVInternett(TESTLINK);
        ArrayList<String> romSomerLedigheldagen = new ArrayList<>();
        for (String rom : CsvReaderUtil.alleRom.keySet()) {
            if (alleRom.get(rom).get(0) == null) {
                romSomerLedigheldagen.add(rom);
            }
            System.out.println(rom);
            System.out.println(Arrays.toString(alleRom.get(rom).toArray()));
        }
        if (romSomerLedigheldagen.isEmpty()) {

        }


    }
}

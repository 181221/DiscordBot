package no.pederyo.util;

import no.pederyo.bot.CsvReader;
import no.pederyo.model.Hendelse;

import java.util.ArrayList;

public class RomFinnerUtil {
    /**
     * sjekker om et rom er ledig gitt klokkeslett.
     *
     * @return Liste med ledige rom.
     */
    public static ArrayList<String> LedigNaa() {
        int naa = DatoOgTimeUtil.hentTime();
        String rommet = "";
        ArrayList<String> ledigeRom = new ArrayList<>();
        for (String rom : CsvReader.alleRom.keySet()) {
            if (CsvReader.alleRom.get(rom) == null) {
                rommet = rom + " er ledig ut dagen.\n";
                ledigeRom.add(rommet);
            } else {
                // sjekk om eventet er nå.
                boolean funnet = false;
                for (int j = 0; j < CsvReader.alleRom.get(rom).size() && !funnet; j++) {
                    Hendelse h = CsvReader.alleRom.get(rom).get(j);
                    int start = DatoOgTimeUtil.parseTime(h.getStart());
                    if (naa + 2 <= start) { //ledig i
                        // ledig
                        rommet = rom + " er ledig til " + h.getStart() + "\n";
                        ledigeRom.add(rommet);
                        funnet = true;
                    } else {
                        break;
                    }
                }
            }
        }
        return ledigeRom;
    }
}

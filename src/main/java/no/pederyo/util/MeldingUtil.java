package no.pederyo.util;

import no.pederyo.bot.CsvReader;

/**
 * Created by Peder on 17.12.2017.
 */
public class MeldingUtil {
    /**
     * Lager en string over alle ledige rom.
     *
     * @return melding med alle rom
     */
    public static String lagMsgFinnAlleRom() {
        StringBuilder sb = new StringBuilder();
        String storrelse = "";
        for (String r : CsvReader.alleRom.keySet()) {
            sb.append(r);
            sb.append(", ");
        }
        if (!CsvReader.alleRom.keySet().isEmpty()) {
            return sb.toString() + CsvReader.alleRom.keySet().size();
        }
        return sb.toString();
    }
}

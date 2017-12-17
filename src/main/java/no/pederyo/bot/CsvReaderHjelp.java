package no.pederyo.bot;

import no.pederyo.model.Hendelse;
import no.pederyo.model.Rom;

import java.util.ArrayList;
import java.util.HashMap;

public class CsvReaderHjelp {
    /**
     * Legger til en hendelse til et tilhørende rom.
     *
     * @param r         Romnavn
     * @param h         Hendelse
     * @param hendelser ti rommet.
     */
    public static synchronized void leggTil(String r, Hendelse h, HashMap<String, ArrayList<Hendelse>> hendelser) {
        ArrayList<Hendelse> k = hendelser.get(r);
        if (k == null) {
            k = new ArrayList<>();
            k.add(h);
            hendelser.put(r, k);
        } else {
            if (!k.contains(h)) k.add(h);
        }
    }

    /**
     * Oppretter en ny hendelse.
     *
     * @param dato
     * @param start
     * @param slutt
     * @return En hendelse
     */
    public static Hendelse lagHendelse(String dato, String start, String slutt) {
        Hendelse h = new Hendelse();
        h.setDato(dato);
        h.setStart(start);
        h.setSlutt(slutt);
        return h;
    }

    /**
     * Oppretter et rom.
     *
     * @param romnavn
     * @return
     */
    public static Rom lagRom(String romnavn) {
        Rom r = new Rom();
        r.setNavn(romnavn);
        return r;
    }
}

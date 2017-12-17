package no.pederyo.util;

import no.pederyo.logg.Logg;
import no.pederyo.model.Hendelse;
import no.pederyo.model.Rom;

import java.util.ArrayList;
import java.util.HashMap;

import static no.pederyo.util.CsvReaderUtil.alleRom;

public class ReaderHjelp {
    private ArrayList<Rom> romMedHendelser;
    private ArrayList<String> ledigerom;
    private ArrayList<Hendelse> ledigehendelser;

    /**
     * Oppretter 3 lister. Allerom, alle ledigerom og alle ledigehendelser.
     */
    public ReaderHjelp() {
        romMedHendelser = new ArrayList<>();
        ledigerom = new ArrayList<>();
        ledigehendelser= new ArrayList<>();
    }

    public static synchronized void leggTil(String r, Hendelse h, HashMap<String, ArrayList<Hendelse>> rom) {
        ArrayList<Hendelse> k = rom.get(r);
        if (k == null) {
            k = new ArrayList<>();
            k.add(h);
            rom.put(r, k);
        } else {
            if (!k.contains(h)) k.add(h);
        }
    }

    public static int parseTime(String time) {
        String ferdig = time.substring(0, 2);
        return Integer.parseInt(ferdig);
    }

    /**
     * Setter opp data fra csv fil.
     * Lager en liste med alle rommene fra csv filen.
     * @param felt Datafelt fra csv fil.
     */
    public void setOppData(String[] felt) {
        String dato = felt[0];
        String start = felt[1].substring(1);
        String slutt = felt[3].substring(1);
        String romnavn = RomUtil.parseRomNavn(felt[5].substring(1));
        Hendelse h = lagHendelse(dato, start, slutt);
        h.setRom(lagRom(romnavn));
        leggTil(romnavn, h, CsvReaderUtil.alleRom);
    }

    /**
     * sjekker om rom finnes.
     * @param r
     * @return
     */
    private boolean inneholder(String r) {
        for (int i = 0; i < romMedHendelser.size(); i++) {
            if (romMedHendelser.get(i).getNavn().equals(r)) {
                return true;
            }
        }
        return false;
    }

    /**
     * finner et rom og legger til en hendelse
     * @param r
     * @param h
     */
    private void finnRomOgLeggTil(String r, Hendelse h) {
        for (int i = 0; i < romMedHendelser.size(); i++) {
            Rom rom = romMedHendelser.get(i);
            if (rom.getNavn() != null) {
                if (r.equals(rom.getNavn())) {
                    romMedHendelser.get(i).getHendelser().add(h);
                    h.setRom(romMedHendelser.get(i));
                }
            }
        }
    }

    /**
     * Lager en string over alle ledige rom.
     * @return
     */
    public String lagMsgFinnLedige() {
        StringBuilder sb = new StringBuilder();
        if (ledigerom.size() != 0) {
            for (String s : ledigerom) {
                sb.append(s);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Lager en string over alle ledige rom.
     * @return
     */
    public String lagMsgFinnAlleRom(HashMap<String, ArrayList<Hendelse>> rom) {
        StringBuilder sb = new StringBuilder();
        for (String r : rom.keySet()) {
            sb.append(r);
            sb.append(", ");
        }
        return sb.toString() + rom.size();
    }

    /**
     * Skriver ut rom og hendelser.
     * @param
     */
    public void printUtRomOgHendelse() {
        for (Rom r : romMedHendelser) {
            System.out.print("romnavn " + r.getNavn() + " ");
            for (Hendelse h : r.getHendelser()) {
                System.out.print(h.toString() + ", ");
            }
            System.out.println();
        }
    }

    /**
     * Logg rom og hendelser.
     *
     * @param
     */
    public void loggRomOgHendelse() {
        StringBuilder sb = new StringBuilder();
        for (Rom r : romMedHendelser) {
            sb.append(r.getNavn() + " ");
            for (Hendelse h : r.getHendelser()) {
                sb.append(h.toString() + ", ");
            }
            sb.append("\n");
        }
        Logg.skrivTilLogg(sb.toString());
    }

    /**
     * Lager en string over alle ledige rom.
     * @return
     */
    public String lagMsg() {
        StringBuilder sb = new StringBuilder();
        if (ledigerom.size() != 0) {
            for (String s : ledigerom) {
                sb.append(s);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Finner rom som ikke har hendelser.
     * @return en liste med rom.
     */
    public ArrayList<Rom> finnTommeRom() {
        ArrayList<Rom> rom = new ArrayList<>();
        for (Rom r : romMedHendelser) {
            if (!(CsvReaderUtil.alleRom.containsKey(r.getNavn()))) {
                rom.add(r);
            }
        }
        return rom;
    }

    public ArrayList<Hendelse> finnLedigeHendelser() {
        //TODO

        return null;
    }

    /**
     * sjekker om et rom er ledig gitt klokkeslett.
     * @return
     */
    public ArrayList<String> LedigNaa() {
        int naa = 8;//RomUtil.hentTime();
        String rommet = "";
        ArrayList<String> ledigeRom = new ArrayList<>();
        int k = 0;
        for (String rom : alleRom.keySet()) {
            if (CsvReaderUtil.alleRom.get(rom) == null) {
                //ledig nå
                rommet = rom + " er ledig ut dagen.\n";
                ledigeRom.add(rommet);
                k++;
            } else {
                // sjekk om eventet er nå.
                boolean funnet = false;
                for (int j = 0; j < CsvReaderUtil.alleRom.get(rom).size() && !funnet; j++) {
                    Hendelse h = CsvReaderUtil.alleRom.get(rom).get(j);
                    int start = parseTime(h.getStart());
                    if (naa + 2 <= start) { //ledig i
                        // ledig
                        rommet = rom + " er ledig til " + h.getStart() + "\n";
                        ledigeRom.add(rommet);
                        funnet = true;
                        k++;
                    } else {
                        break;
                    }
                }
            }
        }
        return ledigeRom;
    }

    /**
     * Finner alle ledige rom på skolen
     *
     * @return en liste med rom.
     */
    public boolean finnAlleLedige() {
        String ledige;
        boolean erLedig = false;
        if (romMedHendelser.size() != 0) {
            for (int i = 0; i < romMedHendelser.size(); i++) {
                Rom r = romMedHendelser.get(i);
                int lengde = r.getHendelser().size();
                Hendelse h = r.getHendelser().get(0);
                if (lengde == 0) {// Rommet er ledig hele dagen.
                    ledige = "Rom: " + r.getNavn() + " Er ledig i hele dag!";
                    ledigerom.add(ledige);
                    erLedig = true;
                } else if (lengde == 1) { // Bare ett event og resten av dagen ledig.
                    ledige = "Rom: " + r.getNavn() + " Er opptatt fra: " + h.getStart() + " til " + h.getSlutt();
                    ledigerom.add(ledige);
                    erLedig = true;
                } else {
                    for (int j = 0; j < lengde - 1; j++) {
                        h = r.getHendelser().get(j);
                        Hendelse h1 = r.getHendelser().get(j + 1);
                        if (erLedig(h, h1)) {
                            ledige = "Rom: " + r.getNavn() + " Er ledig fra: " + h.getSlutt() + " til: " + h1.getStart();
                            ledigerom.add(ledige);
                            ledigehendelser.add(h);
                            erLedig = true;
                        }
                    }
                }
            }
        }
        return erLedig;
    }

    /**
     * Sjekker om er ledig. Et rom er er ledig om differansen er større eller lik 1.
     * @param h
     * @param h1
     * @return
     */
    private boolean erLedig(Hendelse h, Hendelse h1) {
        String slutt = h.getSlutt().substring(0, 2) + h.getSlutt().substring(3, 5);
        String start = h1.getStart().substring(0, 2) + h1.getStart().substring(3, 5);
        int diff = Integer.parseInt(start) - Integer.parseInt(slutt);
        return diff >= 100;
    }

    private Hendelse lagHendelse(String dato, String start, String slutt) {
        Hendelse h = new Hendelse();
        h.setDato(dato);
        h.setStart(start);
        h.setSlutt(slutt);
        return h;
    }

    private Rom lagRom(String romnavn) {
        Rom r = new Rom();
        r.setNavn(romnavn);
        return r;
    }

    public ArrayList<Rom> getAllerom() {
        return romMedHendelser;
    }

    public void setAllerom(ArrayList<Rom> allerom) {
        this.romMedHendelser = allerom;
    }

    public ArrayList<String> getLedigerom() {
        return ledigerom;
    }

    public void setLedigerom(ArrayList<String> ledigerom) {
        this.ledigerom = ledigerom;
    }

    public ArrayList<Hendelse> getLedigehendelser() {
        return ledigehendelser;
    }

    public void setLedigehendelser(ArrayList<Hendelse> ledigehendelser) {
        this.ledigehendelser = ledigehendelser;
    }
}

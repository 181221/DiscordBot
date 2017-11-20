package no.pk.util;

import no.pk.model.Hendelse;
import no.pk.model.Rom;

import java.util.ArrayList;

public class ReaderHjelp {
    private ArrayList<Rom> allerom;
    private ArrayList<String> ledigerom;
    private ArrayList<Hendelse> ledigehendelser;

    /**
     * Oppretter 3 lister. Allerom, alle ledigerom og alle ledigehendelser.
     */
    public ReaderHjelp() {
        allerom = new ArrayList<>();
        ledigerom = new ArrayList<>();
        ledigehendelser= new ArrayList<>();
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
        if (!inneholder(romnavn)) {
            Rom r = lagRom(romnavn);
            r.getHendelser().add(h);
            h.setRom(r);
            allerom.add(r);
        } else {
            finnRomOgLeggTil(romnavn, h);
        }
    }

    /**
     * sjekker om rom finnes.
     * @param r
     * @return
     */
    private boolean inneholder(String r) {
        for (int i = 0; i < allerom.size(); i++) {
            if (allerom.get(i).getNavn().equals(r)) {
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
        for (int i = 0; i < allerom.size(); i++) {
            Rom rom = allerom.get(i);
            if (rom.getNavn() != null) {
                if (r.equals(rom.getNavn())) {
                    allerom.get(i).getHendelser().add(h);
                    h.setRom(allerom.get(i));
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
        for (String s : finnAlleLedige()) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
    /**
     * Lager en string over alle ledige rom.
     * @return
     */
    public String lagMsgFinnAlleRom() {
        StringBuilder sb = new StringBuilder();
        for (Rom r : allerom) {
            sb.append(r.getNavn());
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * sjekker om et rom er ledig gitt klokkeslett.
     * @return
     */
    public String LedigNaa() {
        int naa = RomUtil.hentTime();
        boolean funnet = false;
        String rommet = "Ingen ledige nå";
        System.out.println("før");
        if(ledigehendelser != null){
            for (int i = 0; i < ledigehendelser.size() - 1 && !funnet; i++) {
                Hendelse h = ledigehendelser.get(i);
                Hendelse h1 = ledigehendelser.get(i+1);
                int start = Integer.parseInt(h.getStart().substring(0, 2));
                int slutt = Integer.parseInt(h1.getSlutt().substring(0, 2));
                System.out.println("Start : " + start + " Slutt " + slutt + " nå: " + naa);
                if(naa >= start  && naa <= slutt ) {
                    rommet = "Rom " + h.getRom() + " er ledig til " + slutt;
                    funnet = true;
                }
            }
        }
        return rommet;
    }

    /**
     * Skriver ut rom og hendelser.
     * @param
     */
    public void printUtRomOgHendelse() {
        for (Rom r : allerom) {
            System.out.print("romnavn " + r.getNavn() + " ");
            for (Hendelse h : r.getHendelser()) {
                System.out.print(h.toString() + ", ");
            }
            System.out.println();
        }
    }

    /**
     * Lager en string over alle ledige rom.
     * @return
     */
    public String lagMsg() {
        StringBuilder sb = new StringBuilder();
        for (String s : finnAlleLedige()) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Retrurnerer en liste med alle ledige rom.
     * @return
     */
    public ArrayList<String> finnAlleLedige() {
        String ledige;
        for (int i = 0; i < allerom.size(); i++) {
            Rom r = allerom.get(i);
            int lengde = r.getHendelser().size();
            for (int j = 0; j < lengde - 1; j++) {
                Hendelse h = r.getHendelser().get(j);
                Hendelse h1 = r.getHendelser().get(j + 1);
                if (erLedig(h, h1)) {
                    ledige = "Rom: " + r.getNavn() + " Er ledig fra: " + h.getSlutt() + " til: " + h1.getStart();
                    ledigerom.add(ledige);
                    ledigehendelser.add(h);
                }
            }
        }
        return ledigerom;
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
        return allerom;
    }

    public void setAllerom(ArrayList<Rom> allerom) {
        this.allerom = allerom;
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

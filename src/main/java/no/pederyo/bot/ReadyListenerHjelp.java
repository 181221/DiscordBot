package no.pederyo.bot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import no.pederyo.model.Hendelse;
import no.pederyo.util.MeldingUtil;
import no.pederyo.util.RomFinnerUtil;

import java.util.ArrayList;

/**
 * Hjelpeklasse for botten. Utfører kommandoer fra brukeren.
 */
public class ReadyListenerHjelp {
    public boolean hentLedigeNaa(MessageReceivedEvent event) {
        boolean kjoer = false;
        ArrayList<String> ledigeRom = RomFinnerUtil.LedigNaa();
        String melding = "";
        if (!ledigeRom.isEmpty()) {
            kjoer = true;
            for (String rom : ledigeRom) {
                melding += rom;
            }
        } else {
            melding = "Ingen ledige!";
        }
        event.getChannel().sendMessage(melding).queue();
        return kjoer;
    }

    public boolean hentAlleRom(MessageReceivedEvent event) {
        String melding;
        melding = MeldingUtil.lagMsgFinnAlleRom();
        boolean funnet = true;
        if (melding.equals(null)) {
            melding = "Fant ingen rom";
            funnet = false;
        }
        event.getChannel().sendMessage(melding).queue();
        return funnet;
    }

    /**
     * Bruker kan søke på et seminarrom. Returnerer hendelser.
     *
     * @param event
     */
    public void sokKommando(MessageReceivedEvent event) {
        String message = event.getMessage().getContent().toLowerCase();
        String melding = " ";
        if (message.startsWith("/sok")) {
            String[] kommand = event.getMessage().getContent().split(" ");
            if (kommand.length >= 2) {
                for (int i = 1; i < kommand.length; i++) {
                    if (CsvReader.alleRom.keySet().contains(kommand[i].toUpperCase())) {
                        if (CsvReader.alleRom.get(kommand[i]) != null) {
                            melding += kommand[i] + " \n";
                            for (Hendelse h : CsvReader.alleRom.get(kommand[i].toUpperCase())) {
                                melding += " Start " + h.getStart() + " Slutt " + h.getSlutt() + "\n";
                            }
                        } else {
                            melding += kommand[i] + " er ledig ut dagen. " + "\n";
                        }

                    } else {
                        melding += "Ingen rom med navnet " + kommand[i] + "\n";
                    }
                }
                event.getChannel().sendMessage(melding).queue();
            } else {
                melding = "Skriv inn søke argumenter...";
                event.getChannel().sendMessage(melding).queue();
            }
        }
    }
}

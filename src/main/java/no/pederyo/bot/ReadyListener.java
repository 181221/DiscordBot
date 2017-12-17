package no.pederyo.bot;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import no.pederyo.logg.Logg;
import no.pederyo.model.Hendelse;
import no.pederyo.util.CsvReaderUtil;
import no.pederyo.util.ReaderHjelp;

import java.io.IOException;
import java.util.ArrayList;

import static no.pederyo.util.CsvReaderUtil.readCSVInternett;
import static no.pederyo.util.CsvReaderUtil.readRomCSV;

public class ReadyListener extends ListenerAdapter {
    public static final String TESTLINK = "https://no.timeedit.net/web/hib/db1/service/ri1AY6YYcnd8v5QYwYQrxgb1ZxgYxm98KaYravr5jY5awSadjc8vm5ZQ0Q022x60Yy0505YgX6g5Z5353Yg.html";
    public static final String SEMINARROM = "https://no.timeedit.net/web/hib/db1/service/ri1AY6YYcnd8v5QYwYQrxgb1ZxgYxm98KaYravr5jY5awSadjc8vm5ZQ2Q562x50Yy5603W606g5Z53.html";
    public static ReaderHjelp reader;

    /**
     * Skriver ut alle FINN LEDIGE AUDITORIUM OG SEMINARROM KRONSTAD
     *
     * @throws IOException
     */
    public ReadyListener() {
        try {
            reader = new ReaderHjelp();
            readRomCSV();
            reader = readCSVInternett("https://no.timeedit.net/web/hib/db1/service/ri1AY6YYcnd8v5QYwYQrxgb1ZxgYxm98KaYravr5jY5awSadjc8vm5ZQ2Q052x50Yy5504W606g5Z04.html");
            reader.loggRomOgHendelse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean hentDagensLedigeRom(MessageReceivedEvent event) {
        boolean kjoer = reader.finnAlleLedige();
        String melding = "";
        if (kjoer) {
            melding = reader.lagMsgFinnLedige();
        } else {
            melding = "Ingen ledige rom idag!";
        }
        event.getChannel().sendMessage(melding).queue();
        return kjoer;
    }

    public static boolean hentLedigeNaa(MessageReceivedEvent event) {
        boolean kjoer = false;
        ArrayList<String> ledigeRom = reader.LedigNaa();
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

    public static boolean hentAlleRom(MessageReceivedEvent event) {
        String melding;
        melding = reader.lagMsgFinnAlleRom(CsvReaderUtil.alleRom);
        boolean funnet = true;
        if (melding == null) {
            melding = "Fant ingen rom";
            funnet = false;
        }
        event.getChannel().sendMessage(melding).queue();
        return funnet;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContent().toLowerCase();
        String melding = " ";
        if (message.startsWith("sok")) {
            //søker etter rom.
            String[] kommand = event.getMessage().getContent().split(" ");
            if (kommand.length >= 2) {
                for (int i = 1; i < kommand.length; i++) {
                    if (CsvReaderUtil.alleRom.get(kommand[i].toUpperCase()) != null) {
                        melding += kommand[i] + " \n";
                        for (Hendelse h : CsvReaderUtil.alleRom.get(kommand[i].toUpperCase())) {
                            melding += " Start " + h.getStart() + " Slutt " + h.getSlutt() + "\n";
                        }
                    } else {
                        melding += "ingen rom med navnet " + kommand[i];
                    }
                }
                event.getChannel().sendMessage(melding).queue();
            } else {
                melding = "Skriv inn søke argumenter...";
                event.getChannel().sendMessage(melding).queue();
            }

        }
        if (!(event.getAuthor().getName().equals("Seminar-BoT"))) {
            switch (message) {
                case "hei":
                    event.getChannel().sendMessage("Skriv inn en kommando. For liste av kommandoer skriv: /kommandoer").queue();
                    break;
                case "/ledig":
                    melding = "finner et ledig rom nå!";
                    event.getChannel().sendMessage(melding).queue();
                    hentLedigeNaa(event);
                    break;
                case "/kommandoer":
                    melding = "Dette er kommandoer jeg kan utføre for deg!";
                    event.getChannel().sendMessage(melding).queue();
                    melding = "ledig: finner ledig akkurat nå\n/allerom: allerom på urlen\n/sok romnavn";
                    event.getChannel().sendMessage(melding).queue();
                    break;
                case "/allerom":
                    melding = "henter alle rom på listen.\n" + SEMINARROM;
                    event.getChannel().sendMessage(melding).queue();
                    hentAlleRom(event);
                    break;
            }
        }
    }

    @Override
    public void onReady(ReadyEvent e) {
        String out = "\nThis bot is running on the following servers: \n";

        for (Guild g : e.getJDA().getGuilds()) {
            out += g.getName() + " (" + g.getId() + ") \n";
        }
        Logg.skrivTilLogg(out);
    }


}

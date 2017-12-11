package no.pederyo.bot;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import no.pederyo.util.ReaderHjelp;

import java.io.IOException;

import static no.pederyo.util.CsvReaderUtil.readCSVInternett;

public class ReadyListener extends ListenerAdapter {
    public static final String SEMINARROM = "https://no.timeedit.net/web/hib/db1/service/ri1AY6YYcnd8v5QYwYQrxgb1ZxgYxm98KaYravr5jY5awSadjc8vmvZQgQmZXxcYYy0Y0ZofQ0.html";
    public static ReaderHjelp reader;

    /**
     * Skriver ut alle FINN LEDIGE AUDITORIUM OG SEMINARROM KRONSTAD
     *
     * @throws IOException
     */
    public ReadyListener() {
        try {
            System.out.println("starter reader");
            reader = new ReaderHjelp();
            reader = readCSVInternett(SEMINARROM);
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
        boolean kjoer = reader.finnAlleLedige();
        String melding = "Ingen ledige!";
        if (kjoer) {
            melding = reader.LedigNaa();
        }
        event.getChannel().sendMessage(melding).queue();
        return kjoer;
    }

    public static boolean hentAlleRom(MessageReceivedEvent event) {
        String melding;
        melding = reader.lagMsgFinnAlleRom();
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
        String melding;
        if (!(event.getAuthor().getName().equals("Seminar-BoT"))) {
            switch (message) {
                case "hei":
                    event.getChannel().sendMessage("Skriv inn en kommando. For liste av kommandoer skriv: /kommandoer").queue();
                    break;
                case "/dagens":
                    melding = "finner alle ledige idag..";
                    event.getChannel().sendMessage(melding).queue();
                    hentDagensLedigeRom(event);
                    break;
                case "/ledig":
                    melding = "finner et ledig rom nå!";
                    event.getChannel().sendMessage(melding).queue();
                    hentLedigeNaa(event);
                    break;
                case "/kommandoer":
                    melding = "Dette er kommandoer jeg kan utføre for deg!";
                    event.getChannel().sendMessage(melding).queue();
                    melding = "/dagens: finner alle ledige rom idag\n/ledig: finner ledig akkurat nå\n/allerom: allerom på urlen";
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
        System.out.println(out);
    }


}

package no.pederyo.bot;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import no.pederyo.attributter.Linker;
import no.pederyo.logg.Logg;

import java.io.IOException;

import static no.pederyo.bot.CsvReader.readCSVInternett;
import static no.pederyo.bot.CsvReader.readRomCSV;

public class ReadyListener extends ListenerAdapter {
    private ReadyListenerHjelp rdh;
    /**
     * Finner alle Seminar og Auditorierom på skolen. Legger til i et HashMap. Henter så alle
     * hendelsene til hvert av rommene og legger dem til rommene.
     * @throws IOException
     */
    public ReadyListener() {
        try {
            readRomCSV();
            readCSVInternett(Linker.SEMINARROM);
            rdh = new ReadyListenerHjelp();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContent().toLowerCase();
        String melding = " ";
        rdh.sokKommando(event);
        if (!(event.getAuthor().getName().equals("Seminar-BoT"))) {
            switch (message) {
                case "hei":
                    event.getChannel().sendMessage("Skriv inn en kommando. For liste av kommandoer skriv: /kommandoer").queue();
                    break;
                case "/ledig":
                    melding = "finner et ledig rom nå!";
                    event.getChannel().sendMessage(melding).queue();
                    rdh.hentLedigeNaa(event);
                    break;
                case "/kommandoer":
                    melding = "Dette er kommandoer jeg kan utføre for deg!";
                    event.getChannel().sendMessage(melding).queue();
                    melding = "/ledig: finner ledig akkurat nå\n/allerom: allerom på urlen\n/sok romnavn";
                    event.getChannel().sendMessage(melding).queue();
                    break;
                case "/allerom":
                    melding = "henter alle rom på listen.\n" + Linker.SEMINARROM;
                    event.getChannel().sendMessage(melding).queue();
                    rdh.hentAlleRom(event);
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

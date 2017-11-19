package main.java.no.pederyo;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.*;

/**
 * Created by Peder on 18.11.2017.
 */
public class ReadyListener extends ListenerAdapter{
    @Override
    public void onReady(ReadyEvent e) {
        String out ="\nThis bot is running on the following servers: \n";

        for(Guild g : e.getJDA().getGuilds()) {
            out += g.getName() + " (" + g.getId() + ") \n";
        }
        System.out.println(out);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContent();
        if(!(event.getAuthor().getName().equals("BotenAnna"))){
            switch (message.toLowerCase()) {
                case "hei":
                    event.getChannel().sendMessage("Skriv inn en commando").queue();
                    break;
                case "finnseminar":
                    String melding1 = "Finner alle ledige seminarrom";
                    event.getChannel().sendMessage(melding1).queue();
                    event.getChannel().sendMessage("Executing file...").queue();
                    event.getChannel().sendMessage(kjorFil(event)).queue();
                    break;
                default:
                    System.out.println("lol");
                    String melding = "Hei " + event.getAuthor().getName() + " hva kan jeg hjelpe deg med?";
                    event.getChannel().sendMessage(melding).queue();
        }}else {

        }

    }
    private String kjorFil(MessageReceivedEvent event) {
        String melding = "";
        try {
            String ss = null;
            Runtime obj = null;
            Process p = Runtime.getRuntime().exec("cmd.exe /c java -jar C:\\Users\\Peder\\Documents\\programmering\\timeEdit\\seminar-rom\\out\\artifacts\\seminar_rom_jar\\seminar-rom.jar");
            BufferedWriter writeer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            writeer.write("dir");
            writeer.flush();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((ss = stdInput.readLine()) != null) {
                event.getChannel().sendMessage(ss).queue();
                System.out.println(ss);
            }
            while ((ss = stdError.readLine()) != null) {
                System.out.println(ss);
            }
            melding = "Du får nå en melding om ledige rom! ";

        } catch (IOException e) {
            melding = e.getMessage();
            System.out.println("FROM CATCH" + e.toString());
        }
        return melding;
    }


}

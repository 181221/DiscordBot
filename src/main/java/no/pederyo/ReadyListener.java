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
        if(message.startsWith("finnseminar")) {
            String name = event.getAuthor().getName();
            String response = name + ", hello and welcome ";
            event.getChannel().sendMessage("Executing file...").queue();
            event.getChannel().sendMessage(kjorFil()).queue();
        }
    }
    private String kjorFil() {
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
            System.out.println("Here is the standard output of the command:\n");
            while ((ss = stdInput.readLine()) != null) {
                System.out.println(ss);
            }
            System.out.println("Here is the standard error of the command (if any):\n");
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

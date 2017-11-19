package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * Created by Peder on 18.11.2017.
 */
public class PingCommand  {
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("PONG");
    }
}

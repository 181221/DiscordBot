package no.pk.klient;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import no.pk.bot.ReadyListener;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * Created by Peder on 20.11.2017.
 */
public class Main {
    public static JDA jda;
    public static void main(String[] args) throws IOException, InterruptedException {
        JDABuilder builder = new JDABuilder(AccountType.BOT).addEventListener(new ReadyListener());
        builder.setToken(System.getenv("DISCORD_BOT"));
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        try {
            jda = builder.buildBlocking();
        }catch (LoginException e) {
            e.printStackTrace();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (RateLimitedException e) {
            e.printStackTrace();
        }
    }

}

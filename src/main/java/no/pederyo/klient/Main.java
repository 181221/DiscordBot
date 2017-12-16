package no.pederyo.klient;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import no.pederyo.bot.ReadyListener;
import no.pederyo.logg.Logg;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * Created by Peder on 20.11.2017.
 */
public class Main {
    public static JDA jda;
    public static void main(String[] args) throws IOException, InterruptedException {
        new Logg();
        JDABuilder builder = new JDABuilder(AccountType.BOT).addEventListener(new ReadyListener());
        builder.setToken(args[0]);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        try {
            jda = builder.buildBlocking();
        }catch (LoginException e) {
            e.printStackTrace();
            Logg.skrivTilLogg(e.getMessage());
        }catch (InterruptedException e) {
            e.printStackTrace();
            Logg.skrivTilLogg(e.getMessage());
        }catch (RateLimitedException e) {
            e.printStackTrace();
            Logg.skrivTilLogg(e.getMessage());
        }
    }

}

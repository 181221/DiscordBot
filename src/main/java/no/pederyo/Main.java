package main.java.no.pederyo;


import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static JDA jda;

    public static void main(String[] args) throws IOException, InterruptedException {
        JDABuilder builder = new JDABuilder(AccountType.BOT).addEventListener(new ReadyListener());
        builder.setToken("MzgxNTM5MTA4NzgwMTEzOTIw.DPIuBw.XWbk2APFgVaPN9fE-MhhxynseQg");
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

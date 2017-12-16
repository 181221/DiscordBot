package no.pederyo.logg;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logg {
    public static Logger logger;
    private FileHandler fh;

    public Logg() {
        logger = Logger.getLogger("MyLog");
        settOppLogger();
    }

    public static void skrivTilLogg(String melding) {
        logger.info(melding);
    }

    private void settOppLogger() {
        try {
            fh = new FileHandler("Discordbot.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

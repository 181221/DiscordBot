package no.pederyo.klient;

import no.pederyo.model.Hendelse;
import no.pederyo.model.Rom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Peder on 17.12.2017.
 */
public class mainv2 {
    public static void main(String[] args) {
        HashMap<Rom, ArrayList<Hendelse>> romer = new HashMap<>();
        Rom r = new Rom("j202");
        Rom r2 = new Rom("j202");
        Rom r3 = new Rom("j202");
        romer.put(r, null);
        romer.put(r2, null);
        romer.put(r3, null);
        Rom r4 = new Rom("j202");
        Hendelse h1 = new Hendelse("", "10:30", "11:30", r);
        Hendelse h2 = new Hendelse("", "10:30", "11:30", r2);
        Hendelse h3 = new Hendelse("", "10:30", "11:30", r);
        Hendelse h4 = h1;
        leggTil(r, h1, romer);
        leggTil(r, h2, romer);
        leggTil(r, h2, romer);
        leggTil(r, h2, romer);
        leggTil(r3, h2, romer);
        leggTil(r3, h2, romer);
        leggTil(r2, h3, romer);
        leggTil(r4, h3, romer);
        System.out.println(Arrays.toString(romer.keySet().toArray()));
        for (Hendelse h : romer.get(r)) {
            System.out.println(h.getStart());
        }


    }

    public static synchronized void leggTil(Rom r, Hendelse h, HashMap<Rom, ArrayList<Hendelse>> rom) {
        ArrayList<Hendelse> k = rom.get(r);
        if (k == null) {
            k = new ArrayList<>();
            k.add(h);
            rom.put(r, k);
        } else {
            k.add(h);
        }
    }
}

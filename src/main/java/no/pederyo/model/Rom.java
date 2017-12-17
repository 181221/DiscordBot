package no.pederyo.model;

import java.util.ArrayList;
import java.util.List;

public class Rom {
    private String navn;
    private Boolean harHendelser;
    private List<Hendelse> hendelser;

    public Rom() {
        this("",null,null);
        hendelser = new ArrayList<>();
    }

    public Rom(String navn) {
        this.navn = navn;
        harHendelser = false;
        hendelser = new ArrayList<>();
    }

    public Rom(String navn, Boolean erOpptatt, List<Hendelse> hendelser) {
        this.navn = navn;
        this.harHendelser = erOpptatt;
        hendelser = new ArrayList<>();
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public Boolean getHarHendelser() {
        return harHendelser;
    }

    public void setHarHendelser(Boolean erOpptatt) {
        this.harHendelser = erOpptatt;
    }

    public List<Hendelse> getHendelser() {
        return hendelser;
    }

    public void setHendelser(List<Hendelse> hendelser) {
        this.hendelser = hendelser;
    }

    @Override
    public String toString() {
        return "Rom " + navn + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rom rom = (Rom) o;

        if (navn != null ? !navn.equals(rom.navn) : rom.navn != null) return false;
        if (harHendelser != null ? !harHendelser.equals(rom.harHendelser) : rom.harHendelser != null) return false;
        return hendelser != null ? hendelser.equals(rom.hendelser) : rom.hendelser == null;
    }

    @Override
    public int hashCode() {
        int result = navn != null ? navn.hashCode() : 0;
        result = 31 * result + (harHendelser != null ? harHendelser.hashCode() : 0);
        result = 31 * result + (hendelser != null ? hendelser.hashCode() : 0);
        return result;
    }
}



package Luokat;

import java.util.Random;

public class Tyontekija {

    private static Random randomaattori = new Random();
    private double todennakoisyysToheloida;
    private int toheloinnit;
    private int tehokkuus;
    private int tyontekijakoodi;
    private int tyopaikkaindeksi;
    private double palkka;
    private int kokemus;
    private int keratytResurssit;

    public Tyontekija(double todennakoisyysToheloida, int tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi, double palkka, int kokemus) {
        this.todennakoisyysToheloida = todennakoisyysToheloida;
        this.toheloinnit = 0;
        this.tehokkuus = tehokkuus;
        this.tyontekijakoodi = tyontekijakoodi;
        this.tyopaikkaindeksi = tyopaikkaindeksi;
        this.palkka = palkka;
        this.kokemus = kokemus;
        this.keratytResurssit = 0;
    }

    public Tyontekija(double tohMin, double tohMax, int tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi, double palkka, int kokemus) {
        this(randomaattori.nextDouble() * (tohMax - tohMin) + tohMin, tehokkuus, tyontekijakoodi, tyopaikkaindeksi, palkka, kokemus);
    }

    public double getTodennakoisyysToheloida() {
        return todennakoisyysToheloida;
    }

    public int getToheloinnit() {
        return toheloinnit;
    }

    public void setKeratytResurssit(int keratytResurssit) {
        this.keratytResurssit = keratytResurssit;
    }

    public int getKeratytResurssit() {
        return keratytResurssit;
    }

    public int getTehokkuus() {
        return tehokkuus;
    }

    public int getTyontekijakoodi() {
        return tyontekijakoodi;
    }

    public int getTyopaikkaindeksi() {
        return tyopaikkaindeksi;
    }

    public void setTodennakoisyysToheloida(double todennakoisyysToheloida) {
        this.todennakoisyysToheloida = todennakoisyysToheloida;
    }

    public void setToheloinnit(int toheloinnit) {
        this.toheloinnit = toheloinnit;
    }

    public void setTehokkuus(int tehokkuus) {
        this.tehokkuus = tehokkuus;
    }

    public double getPalkka() {
        return palkka;
    }

    public int getKokemus() {
        return kokemus;
    }

    public void setPalkka(double palkka) {
        this.palkka = palkka;
    }

    public void setKokemus(int kokemus) {
        this.kokemus = kokemus;
    }

    public void setTyontekijakoodi(int tyontekijakoodi) {
        this.tyontekijakoodi = tyontekijakoodi;
    }

    public void setTyopaikkaindeksi(int tyopaikkaindeksi) {
        this.tyopaikkaindeksi = tyopaikkaindeksi;
    }
    public Boolean toheloiko(){
        if (randomaattori.nextDouble() < todennakoisyysToheloida){
            toheloinnit += 1;
            return true;
        }
        return false;
    }
}

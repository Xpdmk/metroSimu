package luokat;

import java.util.Random;

public class Tyontekija {

    private static Random randomaattori = new Random();
    private double todennakoisyysToheloida;
    private int toheloinnit;
    private double tehokkuus;
    private int tyontekijakoodi;
    private int tyopaikkaindeksi;

    public Tyontekija(double todennakoisyysToheloida, double tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi) {
        this.todennakoisyysToheloida = todennakoisyysToheloida;
        this.tehokkuus = tehokkuus;
        this.tyontekijakoodi = tyontekijakoodi;
        this.tyopaikkaindeksi = tyopaikkaindeksi;
        this.toheloinnit = 0;
    }

    public Tyontekija(double tohMin, double tohMax, double tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi) {
        this(randomaattori.nextDouble() * (tohMax - tohMin) + tohMin, tehokkuus, tyontekijakoodi, tyopaikkaindeksi);
    }

    public double getTodennakoisyysToheloida() {
        return todennakoisyysToheloida;
    }

    public int getToheloinnit() {
        return toheloinnit;
    }

    public double getTehokkuus() {
        return tehokkuus;
    }

    public int getTyontekijakoodi() {
        return tyontekijakoodi;
    }

    public int getTyopaikkaindeksi() {
        return tyopaikkaindeksi;
    }

}

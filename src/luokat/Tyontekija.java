package luokat;

import static java.lang.Math.round;
import java.util.Random;

public class Tyontekija {

    private static Random randomaattori = new Random();
    private double todennakoisyysToheloida;
    private int toheloinnit;
    private double tehokkuus;
    private int tyontekijakoodi;
    private int tyopaikkaindeksi;
    private double palkka;
    private int kokemus;
    final private int tohMin = 1;
    private long tohMax;

    public Tyontekija(double todennakoisyysToheloida, double tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi, double palkka, int kokemus) {
        this.todennakoisyysToheloida = todennakoisyysToheloida;
        this.toheloinnit = 0;
        this.tehokkuus = tehokkuus;
        this.tyontekijakoodi = tyontekijakoodi;
        this.tyopaikkaindeksi = tyopaikkaindeksi;
        this.palkka = palkka;
        this.kokemus = kokemus;
        this.tohMax= Math.round(randomaattori.nextDouble()*10-0+0);
    }

    public Tyontekija(double tohMin, double tohMax, double tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi, double palkka, int kokemus) {
        this(randomaattori.nextDouble() * (tohMax - tohMin) + tohMin, tehokkuus, tyontekijakoodi, tyopaikkaindeksi, palkka, kokemus);
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

    public void setTodennakoisyysToheloida(double todennakoisyysToheloida) {
        this.todennakoisyysToheloida = todennakoisyysToheloida;
    }

    public void setToheloinnit(int toheloinnit) {
        this.toheloinnit = toheloinnit;
    }

    public void setTehokkuus(double tehokkuus) {
        this.tehokkuus = tehokkuus;
    }

    public void setTyontekijakoodi(int tyontekijakoodi) {
        this.tyontekijakoodi = tyontekijakoodi;
    }

    public void setTyopaikkaindeksi(int tyopaikkaindeksi) {
        this.tyopaikkaindeksi = tyopaikkaindeksi;
    }
    public Boolean toheloiko(){
        if (randomaattori.nextDouble()*tohMax-tohMin+tohMin < todennakoisyysToheloida){
            toheloinnit += 1;
            return true;
        }
        return false;
    }
}

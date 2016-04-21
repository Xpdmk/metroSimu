package luokat;
import java.util.Random;

public class Tyontekija {
  private static Random randomaattori = new Random();
  private double todennakoisyysToheloida;
  private double tehokkuus;
  private int tyontekijakoodi;
  private int tyopaikkaindeksi;
  public Tyontekija(double todennakoisyysToheloida, double tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi) {
    this.todennakoisyysToheloida = todennakoisyysToheloida;
    this.tehokkuus = tehokkuus;
    this.tyontekijakoodi = tyontekijakoodi;
    this.tyopaikkaindeksi = tyopaikkaindeksi;
  }
  public Tyontekija(double tohMin, double tohMax, double tehokkuus, int tyontekijakoodi, int tyopaikkaindeksi) {
    this(randomaattori.nextDouble()*(tohMax-tohMin)+tohMin, tehokkuus, tyontekijakoodi, tyopaikkaindeksi);
  }
}

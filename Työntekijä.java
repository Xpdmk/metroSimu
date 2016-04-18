import java.util.Random;

public class Työntekijä {
  private static Random randomaattori;
  private double todennäköisyysToheloida;
  private double tehokkuus;
  private int työntekijäkoodi;
  private int työpaikkaindeksi;
  public Työntekijä(double todennäköisyysToheloida, double tehokkuus, int työntekijäkoodi, int työpaikkaindeksi) {
    this.randomaattori = new Random();
    this.todennäköisyysToheloida = todennäköisyysToheloida;
    this.tehokkus = tehokkuus;
    this.työntekijäkoodi = työntekijäkoodi;
    this.työpaikkaindeksi = työpaikkaindeksi;
  }
  public Työntekijä(double tohMin, double tohMax, double tehokkuus, int työntekijäkoodi, int työpaikkaindeksi) {
    randomaattori = new Random();
    this(randomaattori.nextDouble(tohmax-tohmin)+tohmin, tehokkuus, työntekijäkoodi, työnpaikkaindeksi);
  }
}

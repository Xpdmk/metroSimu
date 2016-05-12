package Luokat;
import java.util.ArrayList;

public class Raportti {
  private final int saadutAteriat;
  //private Map<Integer, Integer> saadutMineraalitPerMineraalikoodi;
  private final int saatuPuu;
  private final ArrayList<Integer> toheloijat;

  public Raportti(int saadutAteriat, int saatuPuu, ArrayList<Integer> toheloijat) {
    this.saadutAteriat = saadutAteriat;
    this.saatuPuu = saatuPuu;
    this.toheloijat = toheloijat;
  }

    public int getSaadutAteriat() {
        return saadutAteriat;
    }

    public int getSaatuPuu() {
        return saatuPuu;
    }

    public ArrayList<Integer> getToheloijat() {
        return toheloijat;
    }



}

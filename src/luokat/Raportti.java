package luokat;
import java.util.ArrayList;

public class Raportti {
  private final int saadutAteriat;
  //private Map<Integer, Integer> saadutMineraalitPerMineraalikoodi;
  private final int saatuPuu;
  private final ArrayList<Integer> toheloijat;
  private final double myyntitulot;

  public Raportti(int saadutAteriat, int saatuPuu, ArrayList<Integer> toheloijat, double myyntitulot) {
    this.saadutAteriat = saadutAteriat;
    this.saatuPuu = saatuPuu;
    this.toheloijat = toheloijat;
    this.myyntitulot = myyntitulot;
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

    public double getMyyntitulot() {
        return myyntitulot;
    }
  
  

}

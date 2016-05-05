package luokat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Raportti {
  private int saadutAteriat;
  //private Map<Integer, Integer> saadutMineraalitPerMineraalikoodi;
  private int saatuPuu;
  private ArrayList<Integer> toheloijat;
  private double myyntitulot;

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

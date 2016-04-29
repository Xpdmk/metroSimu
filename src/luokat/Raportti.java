package luokat;
import java.util.ArrayList;
import java.util.Map;

public class Raportti {
  private int saadutAteriat;
  //private Map<Integer, Integer> saadutMineraalitPerMineraalikoodi;
  private int saatuPuu;
  private Map<Integer, Integer> toheloinnitPerTyontekijakoodi;
  private double myyntitulot;

  public Raportti(int saadutAteriat, int saatuPuu, ArrayList<Integer> toheloijat, double myyntitulot) {
    this.saadutAteriat = saadutAteriat;
    this.saatuPuu = saatuPuu;
    this.toheloinnitPerTyontekijakoodi = toheloinnitPerTyontekijakoodi;
    this.myyntitulot = myyntitulot;
  }

}

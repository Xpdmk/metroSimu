public class Raportti {
  private int saadutAteriat;
  //private Map<Integer, Integer> saadutMineraalitPerMineraalikoodi;
  private int saatuPuu;
  private Map<Integer, Integer> toheloinnitPerTyöntekijäkoodi;
  private double myyntitulot;

  public Raportti(int saadutAteriat, int saatuPuu, Map<Integer, Integer> toheloinnitPerTyöntekijäkoodi, double myyntitulot) {
    this.saadutAteriat = saadutAteriat;
    this.saatuPuu = saatuPuu;
    this.toheloinnitPerTyöntekijäkoodi = toheloinnitPerTyöntekijäkoodi;
    this.myyntitulot = myyntitulot;
  }

}

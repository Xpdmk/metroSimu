
package Kauppanakyma;

import javafx.scene.control.TextField;

public class Kauppatuoterivi {
    private final String tuotteenNimi;
    private final int tuotteenMaara;
    private final double tuotteenHinta;
    private final TextField syote;
    
    public Kauppatuoterivi(String tuotteenNimi, int tuotteenMaara, double tuotteenHinta) {
        this.tuotteenNimi = tuotteenNimi;
        this.tuotteenMaara = tuotteenMaara;
        this.tuotteenHinta = tuotteenHinta;
        this.syote = new TextField();
        syote.setPromptText("Montako myydään");
        
    }

    public String getTuotteenNimi() {
        return tuotteenNimi;
    }

    public int getTuotteenMaara() {
        return tuotteenMaara;
    }

    public double getTuotteenHinta() {
        return tuotteenHinta;
    }

    public TextField getSyote() {
        return syote;
    }
    
    
    
}


package Kauppanakyma;

import javafx.scene.control.TextField;

public class Kauppatuoterivi {
    private String tuotteenNimi;
    private int tuotteenMaara;
    private double tuotteenHinta;
    private TextField syote;
    
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

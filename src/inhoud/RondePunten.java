/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inhoud;

/**
 *
 * @author Ellen
 */
public class RondePunten {

    private int[] punten;

    public RondePunten() {
        punten = new int[Ronde.AANTALRONDE - 1];
    }

    public int getPuntenRonde(Ronde ronde) {
        if (ronde.getNummer() < Ronde.AANTALRONDE) {
            return punten[ronde.getNummer()-1];
        }else{
            int som=0;
            for(int i:punten){
                som+=i;
            }
            return som;
        }
    }
    
    public void addPunten(Ronde ronde, int p){
         if (ronde.getNummer() < Ronde.AANTALRONDE) {
             punten[ronde.getNummer()-1]+= p;
        }
        
    }

    @Override
    public String toString() {
        String s="";
        for(int i=0; i<punten.length;i++){
            s+= (i+1) +":"+punten[i] + "  ";
        }
        
        return "RondePunten{" +  s + '}';
    }
    
    

}

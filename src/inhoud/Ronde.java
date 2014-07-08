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
public enum Ronde {
    EEN(1, "zin") {
        @Override
        public Ronde volgende(){
            return Ronde.TWEE;
        }
        
        @Override
        public boolean hasVolgende(){
            return true;
        }
    }, 
    TWEE (2, "1 woord"){
        @Override
        public Ronde volgende(){
            return Ronde.TWEE;
        }
        
        @Override
        public boolean hasVolgende(){
            return true;
        }
    }, 
    DRIE(3, "uitbeelden") {
        @Override
        public Ronde volgende(){
            return Ronde.DRIE;
        }
        
        @Override
        public boolean hasVolgende(){
            return true;
        }
    }, 
    UITSLAG(4, null) {
        @Override
        public Ronde volgende(){
            return null;
        }
        
        @Override
        public boolean hasVolgende(){
            return false;
        }
    };
    
    public static final int AANTALRONDE=4;
    
    
    
    private final int nummer;
    private final String regel;

    private Ronde(int nummer, String regel) {
        this.nummer = nummer;
        this.regel=regel;
    }

    public int getNummer() {
        return nummer;
    }
    
    public abstract Ronde volgende();
    public abstract boolean hasVolgende();
    
    
}

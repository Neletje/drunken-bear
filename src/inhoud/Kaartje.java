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
public class Kaartje {
    private static int ID = 1;
    
    private String woord;
    private final int id;
    public Kaartje() {
        id=ID++;
    }

    public String getWoord() {
        return woord;
    }

    public void setWoord(String woord) {
        this.woord = woord;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Kaartje{" + "woord=" + woord + ", id=" + id + '}';
    }
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package inhoud;

import java.util.ArrayList;
import java.util.List;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;

/**
 *
 * @author Ellen
 */
public class Kaartje implements mijnObserver{
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
        if(this.woord == null ? woord != null : !this.woord.equals(woord)){
        this.woord = woord;
            invalidate(Onderwerp.KAARTJES_EDIT);
        }
    }
    

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Kaartje{" + "woord=" + woord + ", id=" + id + '}';
    }

    
    
    
    private List<mijnLuisteraar> luisteraars = new ArrayList<mijnLuisteraar>();
    private List<Onderwerp> onderwerpen = new ArrayList<Onderwerp>();
    
    @Override
    public void addLuisteraar(mijnLuisteraar luisteraar, Onderwerp onderwerp) {
        luisteraars.add(luisteraar);
        onderwerpen.add(onderwerp);

    
    }

    @Override
    public void removeLuisteraar(mijnLuisteraar luisteraar, Onderwerp onderwerp) {
        int i=luisteraars.indexOf(luisteraar);
        onderwerpen.remove(i);
        luisteraars.remove(i);
    }
    
    private void invalidate(Onderwerp onderwerp){
        for(int i=0;i<luisteraars.size();i++){
            if(onderwerpen.get(i)==onderwerp){
                luisteraars.get(i).invalidate(this, onderwerp);
            }
        }
    }
    
    
    
    
}

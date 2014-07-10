/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scherm.spel;

import java.util.ArrayList;
import java.util.List;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;

/**
 *
 * @author Ellen
 */
public class TimerModel implements mijnObserver {

    private boolean spelBezig;

    public synchronized boolean isSpelBezig() {
        return spelBezig;
    }

    public void setSpelBezig(boolean spelBezig) {
        if(spelBezig!=this.spelBezig){
            synchronized(this){
                this.spelBezig = spelBezig;
            }
            invalidate(Onderwerp.TIMER_KLAAR);
        }
    }

    public TimerModel() {
        this.spelBezig = false;
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
        int i = luisteraars.indexOf(luisteraar);
        onderwerpen.remove(i);
        luisteraars.remove(i);
    }

    private void invalidate(Onderwerp onderwerp) {
        for (int i = 0; i < luisteraars.size(); i++) {
            if (onderwerpen.get(i) == onderwerp) {
                luisteraars.get(i).invalidate(this, onderwerp);
            }
        }
    }
}

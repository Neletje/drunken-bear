/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package luisteraar;

/**
 *
 * @author Ellen
 */
public interface mijnObserver {
    
    public void addLuisteraar(mijnLuisteraar luisteraar, Onderwerp onderwerp);
    public void removeLuisteraar(mijnLuisteraar luisteraar, Onderwerp onderwerp);
    
}

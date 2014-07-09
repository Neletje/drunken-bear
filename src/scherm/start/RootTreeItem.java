/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scherm.start;

import inhoud.Kaartje;
import inhoud.Model;
import inhoud.Speler;
import java.util.List;
import javafx.scene.control.TreeItem;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;

/**
 *
 * @author Ellen
 */
public class RootTreeItem extends TreeItem<String> implements mijnLuisteraar{

    public RootTreeItem() {
        this.setValue("Spelers");
        Model.getInstance().addLuisteraar(this, Onderwerp.SPELERS_ADD);
        
        List<Speler> spelers = Model.getInstance().getSpelers();
        Kaartje[] kaartjes;
        
        for (int i = 0; i < spelers.size() ; i++) {
            TreeItem<String> item = new SpelerTreeItem(spelers.get(i));            
            getChildren().add(item);
            /*
            kaartjes = Model.getInstance().getKaartjes(spelers.get(i));
            for(int j=0; j<kaartjes.length;j++ ){
                TreeItem<String> item2 = new TreeItem<String> (""+kaartjes[j].getWoord());            
                item.getChildren().add(item2);
            }
                    */
        } 
    }

    
    
    
    
    @Override
    public void invalidate(mijnObserver observer, Onderwerp onderwerp) {
        if(onderwerp==Onderwerp.SPELERS_ADD){
            List<Speler> spelers = Model.getInstance().getSpelers();
            int i=spelers.size()-1;
            TreeItem<String> item = new SpelerTreeItem(spelers.get(i));            
            getChildren().add(item);
        
        }

    }
    
    
    
}

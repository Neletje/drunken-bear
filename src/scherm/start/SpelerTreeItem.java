/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scherm.start;

import inhoud.Kaartje;
import inhoud.Model;
import inhoud.Speler;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Ellen
 */
public class SpelerTreeItem extends TreeItem<String> {
    
    private Speler speler;

    public SpelerTreeItem(Speler speler) {
        this.speler = speler;
        this.setValue(speler.getName());
        Kaartje[] kaartjes = Model.getInstance().getKaartjes(speler);
            for(int j=0; j<kaartjes.length;j++ ){
                TreeItem<String> item2 = new KaartjeTreeItem(kaartjes[j]);            
                getChildren().add(item2);
            }
    }
    
    
    
}

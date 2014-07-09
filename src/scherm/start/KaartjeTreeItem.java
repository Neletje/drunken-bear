/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scherm.start;

import inhoud.Kaartje;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;

/**
 *
 * @author Ellen
 */
public class KaartjeTreeItem extends TreeItem<String> implements mijnLuisteraar {

    private Kaartje kaartje;
    private TextField field;

    public KaartjeTreeItem(Kaartje kaartje) {
        this.kaartje = kaartje;
        this.setValue(""); //anders is textfield niet te zien
        field = new TextField(kaartje.getWoord());
        this.setGraphic(field);
        maakField();
        kaartje.addLuisteraar(this, Onderwerp.KAARTJES_EDIT);
    }

    private void maakField() {
        field.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    kaartje.setWoord(field.getText());
                }
            }
        });
       field.focusedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!field.isFocused()
                        &
                        ((kaartje.getWoord()==null && !"".equals(field.getText()))
                        || (kaartje.getWoord()!=null && !kaartje.getWoord().equals(field.getText())))
                        ){
                    kaartje.setWoord(field.getText());
                
                }
            
            }
        });

    }

    @Override
    public void invalidate(mijnObserver observer, Onderwerp onderwerp) {
        if (onderwerp == Onderwerp.KAARTJES_EDIT) {
            field.setText("" + kaartje.getWoord());
        }

    }

}

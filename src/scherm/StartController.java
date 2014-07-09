/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scherm;

import exceptions.GameBezigException;
import exceptions.OngeldigSpelException;
import inhoud.Kaartje;
import inhoud.Model;
import inhoud.Speler;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import scherm.start.RootTreeItem;
import spel.Spel;
import spel.Status;

/**
 * FXML Controller class
 *
 * @author Ellen
 */
public class StartController implements Initializable,InvalidationListener {
    @FXML
    private Button voegtoeButton;
    @FXML
    private TextField naamField;
    @FXML
    private TreeView<String> view;
    @FXML
    private Label opmLabel;
    @FXML
    private Button startButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        test();
        maakTree();
        Spel.getInstance().addListener(this);
    }    

    @FXML
    private void voegtoe(ActionEvent event) throws GameBezigException {
        Speler sp = new Speler(naamField.getText());
        Model.getInstance().addSpeler(sp);
        System.out.println("voeg toe"+sp);
    }

    @FXML
    private void start(ActionEvent event) throws GameBezigException, OngeldigSpelException {
        System.out.println("start");
        Model.getInstance().startSpel();
        Spel.getInstance().setStatus(Status.RONDE);
        
    }

    @Override
    public void invalidated(Observable observable) {
        if(Spel.getInstance().getStatus()==Status.START){
            voegtoeButton.setDisable(false);
            startButton.setDisable(false);
            //TODO view editeerbaar 
        }else{
            voegtoeButton.setDisable(true);
            startButton.setDisable(true);
        }
    
    }
    
    
    private void maakTree(){
        TreeItem<String> rootItem = new RootTreeItem();
        rootItem.setExpanded(true);
        view.setShowRoot(false);
        view.setRoot(rootItem);
    }

    private void test() {
        Speler[] spelers = new Speler[]{new Speler("A"), new Speler("B"), new Speler("C"), new Speler("D")};
         int i=0;
         for (Speler s : spelers) {
            try {
                Model.getInstance().addSpeler(s);
                Kaartje[] k = Model.getInstance().getKaartjes(s);
                
                for(Kaartje a: k){
                    a.setWoord(s.getName()+(i++));
                }
            } catch (GameBezigException ex) {
                System.err.println(ex);
            }
        }
    }
}

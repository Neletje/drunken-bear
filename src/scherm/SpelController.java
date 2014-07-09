/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scherm;

import inhoud.Model;
import inhoud.Ronde;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;

/**
 * FXML Controller class
 *
 * @author Ellen
 */
public class SpelController implements Initializable, mijnLuisteraar {

    @FXML
    private Label ronde;
    @FXML
    private Label voor;
    @FXML
    private Label regel;
    @FXML
    private Label woord;
    @FXML
    private Label tijd;
    private Timer t;
    private int counter;
    private final int TIME=30;
    @FXML
    private Button startbutton;
    @FXML
    private Button okbutton;
    @FXML
    private Button valsbutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Model.getInstance().addLuisteraar(this, Onderwerp.RONDE_VOLGENDE);
        invalidate(null, Onderwerp.RONDE_VOLGENDE);
        t = new Timer(true);
        herstart();
        tijd.setText(TIME+"");

        

    }
    
 

    @FXML
    private void start(ActionEvent event) {
        initDeel();
        startbutton.setVisible(false);
        startbutton.setDisable(true);
        okbutton.setDisable(false);
        valsbutton.setDisable(false);
    }

    @FXML
    private void okgeklikt(ActionEvent event) {
        
    }

    @FXML
    private void valsgeklikt(ActionEvent event) {
    }

    @Override
    public void invalidate(mijnObserver observer, Onderwerp onderwerp) {
        Ronde r = Model.getInstance().getRonde();
        ronde.setText(r.toString());
        regel.setText(r.getRegel());
    }

    private void initDeel() {
        counter = TIME;
        tijd.setText(TIME+"");
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        herstart();
                        
                    }
                });
            }
        }, TIME*1000);
/*
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                       tijd.setText(--counter+"");

                    }
                });
            }
        }, 0,1000);
    }
        */
        for(int i=1; i<=TIME;i++){
            
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                             tijd.setText(--counter+"");
                        }
                    });
                }
        }, i*1000);
            
        }
    
    
    }

    private void herstart() {
        woord.setText("!");
        startbutton.setVisible(true);
        startbutton.setDisable(false);
        okbutton.setDisable(true);
        valsbutton.setDisable(true);
        

    }

}

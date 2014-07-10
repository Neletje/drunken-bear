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
import inhoud.Ronde;
import inhoud.RondePunten;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;
import scherm.spel.TimerModel;
import spel.Spel;
import spel.Status;

/**
 * FXML Controller class
 *
 * @author Ellen
 */
public class SpelController implements Initializable, mijnLuisteraar,InvalidationListener {

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
    private static final int TIME = 10;
    @FXML
    private Button startbutton;
    @FXML
    private Button okbutton;
    @FXML
    private Button valsbutton;
    private TimerTask task;
    private TimerTask[] tasklist;

    private TimerModel timerModel;
    private String s;
    private List<Kaartje> kaartjes;
    private Kaartje  huidigKaartje;
    private Random random;
    private RondePunten p;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Model.getInstance().addLuisteraar(this, Onderwerp.RONDE_VOLGENDE);
        Spel.getInstance().addListener(this);
        invalidate(null, Onderwerp.RONDE_VOLGENDE);
        
        t = new Timer(true);
        herstart();
        tijd.setText(TIME + "");
        tasklist = new TimerTask[TIME];

        timerModel = new TimerModel();
        timerModel.addLuisteraar(this, Onderwerp.TIMER_KLAAR);
        //dit is een zeer lelijke cheat, maar kheb geen zin om uit te zoeken hoe het mooi op te lossen dus #yolo
        timerModel.setSpelBezig(true);
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timerModel.setSpelBezig(false);
                    }
                });
            }
        }, 0);
       

    }

    private int tellerPunten;
    
    @FXML
    private void start(ActionEvent event) {
        initTimerDeel();
        startbutton.setVisible(false);
        startbutton.setDisable(true);
        okbutton.setDisable(false);
        valsbutton.setDisable(false);
        timerModel.setSpelBezig(true);
        tellerPunten=0;
        kieskaartje();
    }

    @FXML
    private void okgeklikt(ActionEvent event) {
        if(huidigKaartje!=null){
        tellerPunten++;
        }
        kaartjes.remove(huidigKaartje);
        if(kaartjes.size()>0){
            kieskaartje();
        }else{
            huidigKaartje=null;
            //ronde gedaan
            System.out.println("ronde gedaan");
            cancel();
            volgende();
            Spel.getInstance().setStatus(Status.PAUZE);
        }
        
        

    }

    @FXML
    private void valsgeklikt(ActionEvent event) {
        System.out.println("cancel");

        cancel();
        volgende();
    }

    private void cancel() {
        task.cancel();
        for (int i = 0; i < TIME; i++) {
            tasklist[i].cancel();
        }
    }

    @Override
    public void invalidate(mijnObserver observer, Onderwerp onderwerp) {
        if (onderwerp == Onderwerp.RONDE_VOLGENDE) {
            Ronde r = Model.getInstance().getRonde();
            ronde.setText(r.toString());
            regel.setText(r.getRegel());
            kaartjes=Model.getInstance().getKaartjes();
        } else if (onderwerp == Onderwerp.TIMER_KLAAR) {
            if (!timerModel.isSpelBezig()) {
                startbutton.setVisible(true);
                startbutton.setDisable(false);
                volgende();
            }

        }
    }

    private void initTimerDeel() {
        counter = TIME;
        tijd.setText(TIME + "");
        task = new TimerTask() {

            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timerModel.setSpelBezig(false);
                    }
                });
            }
        };
        t.schedule(task, TIME * 1000);

        for (int i = 1; i <= TIME; i++) {

            tasklist[i - 1] = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            tijd.setText(--counter + "");
                        }
                    });
                }
            };

            t.schedule(tasklist[i - 1], i * 1000);

        }

    }

    private void herstart() {
        voor.setText(s);
        startbutton.setVisible(true);
        startbutton.setDisable(false);
        okbutton.setDisable(true);
        valsbutton.setDisable(true);

    }

    private void volgende() {
        //bij cheat dus niet ;)
        if(tellerPunten>0){
           p=Model.getInstance().getPunten( Model.getInstance().getHuidigeSpeler());
           p.addPunten(Model.getInstance().getRonde(), tellerPunten);
        }
        
        
        try {
            //volgende speler oproepen
            Model.getInstance().volgendeSpeler();

            System.out.println("volgende");
        } catch (GameBezigException ex) {
            System.err.println(ex);
        }
            s = Model.getInstance().getHuidigeSpeler().getName() + " voor " + Model.getInstance().getPartner(Model.getInstance().getHuidigeSpeler()).getName();

        herstart();

    }

    @Override
    public void invalidated(Observable observable) {
        Spel spel = (Spel) observable;
        if(spel.getStatus()==Status.RONDE){
            kaartjes = Model.getInstance().getKaartjes();
            random = new Random();
        }
    
    }

    private void kieskaartje() {
        if(kaartjes.size()>1){
        huidigKaartje = kaartjes.get(random.nextInt(kaartjes.size()));
        woord.setText(huidigKaartje.getWoord());
        }else if(kaartjes.size()==1){
            huidigKaartje = kaartjes.get(0);
            woord.setText(huidigKaartje.getWoord());
        }
        
        
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scherm;

import exceptions.GameBezigException;
import inhoud.Model;
import inhoud.Ronde;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import luisteraar.Onderwerp;
import luisteraar.mijnLuisteraar;
import luisteraar.mijnObserver;
import scherm.spel.TimerModel;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Model.getInstance().addLuisteraar(this, Onderwerp.RONDE_VOLGENDE);
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

    @FXML
    private void start(ActionEvent event) {
        initTimerDeel();
        startbutton.setVisible(false);
        startbutton.setDisable(true);
        okbutton.setDisable(false);
        valsbutton.setDisable(false);
        timerModel.setSpelBezig(true);

    }

    @FXML
    private void okgeklikt(ActionEvent event) {

    }

    @FXML
    private void valsgeklikt(ActionEvent event) {
        System.out.println("cancel");

        task.cancel();
        for (int i = 0; i < TIME; i++) {
            tasklist[i].cancel();
        }
        volgende();
    }

    @Override
    public void invalidate(mijnObserver observer, Onderwerp onderwerp) {
        if (onderwerp == Onderwerp.RONDE_VOLGENDE) {
            Ronde r = Model.getInstance().getRonde();
            ronde.setText(r.toString());
            regel.setText(r.getRegel());
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
        woord.setText("!");
        startbutton.setVisible(true);
        startbutton.setDisable(false);
        okbutton.setDisable(true);
        valsbutton.setDisable(true);

    }

    private void volgende() {

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

}

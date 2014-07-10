/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scherm;

import exceptions.OngeldigSpelException;
import inhoud.Model;
import inhoud.Ronde;
import inhoud.RondePunten;
import inhoud.Speler;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import spel.Spel;
import spel.Status;

/**
 * FXML Controller class
 *
 * @author Ellen
 */
public class StatistiekController implements Initializable, InvalidationListener {

    @FXML
    private TableView<Paar> tabel;
    @FXML
    private TableColumn<Paar, String> team;
    @FXML
    private TableColumn<Paar, String> score;
    @FXML
    private Button next;

    private ObservableList<Paar> paar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Spel.getInstance().addListener(this);
        paar = FXCollections.observableArrayList();
        tabel.getColumns().clear();
        team = new TableColumn<>("team");
        team.setCellValueFactory(new PropertyValueFactory<Paar, String>("team"));
        score = new TableColumn<>("score");
        score.setCellValueFactory(new PropertyValueFactory<Paar, String>("score"));
        tabel.getColumns().add(team);
        tabel.getColumns().add(score);
        tabel.setItems(paar);
    }

    @FXML
    private void volgendeRonde(ActionEvent event) {
        try {
            Model.getInstance().volgenderonde();
            System.out.println("volgende RONDE !!");
        } catch (OngeldigSpelException ex) {
            System.err.println(ex);
        }
        Spel.getInstance().setStatus(Status.RONDE);
        
    }

    @Override
    public void invalidated(Observable observable) {
        Spel spel = (Spel) observable;
        if (spel.getStatus() == Status.PAUZE) {
            paar.clear();
            //update lijst
            List<Speler> spelers = Model.getInstance().getSpelers();
            List<RondePunten> punten = Model.getInstance().getPunten();

            for (int i = 0; i < spelers.size() / 2; i++) {
                String t = (spelers.get(i).getName() + " & " + spelers.get(i + spelers.size() / 2).getName());
                RondePunten p = punten.get(i);
                String ps = p.getPuntenRonde(Ronde.UITSLAG) + "";
                Paar pr = new Paar(t, ps);
                paar.add(pr);
            }

        }
        
        if(Model.getInstance().hasvolgenderonde()){
            next.setVisible(true);
            next.setDisable(false);
        }else{
            next.setVisible(false);
            next.setDisable(true);
        }
    }

    public class Paar {

        private String team;
        private String score;

        public Paar(String team, String score) {
            this.team = team;
            this.score = score;
        }

        public String getTeam() {
            return team;
        }

        public void setTeam(String team) {
            this.team = team;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }



    }

}
